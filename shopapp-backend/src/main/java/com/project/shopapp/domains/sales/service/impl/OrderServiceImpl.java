// --- service/impl/OrderServiceImpl.java ---
package com.project.shopapp.domains.sales.service.impl;

import com.project.shopapp.domains.marketing.api.CouponInternalApi;
import com.project.shopapp.domains.sales.api.CartInternalApi;
import com.project.shopapp.domains.sales.api.CartItemInternalApi;
import com.project.shopapp.domains.sales.dto.nested.CartItemBasicDto;
import com.project.shopapp.domains.sales.dto.request.OrderCheckoutRequest;
import com.project.shopapp.domains.sales.dto.response.OrderResponse;
import com.project.shopapp.domains.sales.entity.Order;
import com.project.shopapp.domains.sales.enums.OrderChannel;
import com.project.shopapp.domains.sales.enums.OrderStatus;
import com.project.shopapp.domains.sales.enums.PaymentStatus;
import com.project.shopapp.domains.sales.event.OrderCreatedEvent;
import com.project.shopapp.domains.sales.mapper.OrderMapper;
import com.project.shopapp.domains.sales.repository.OrderRepository;
import com.project.shopapp.domains.sales.service.OrderService;
import com.project.shopapp.shared.base.PageResponse;
import com.project.shopapp.shared.exceptions.InvalidParamException;
import com.project.shopapp.shared.exceptions.ConflictException;
import com.project.shopapp.shared.exceptions.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;

    // Các Facade gọi sang Module khác
    private final CartItemInternalApi cartItemApi;
    private final CartInternalApi cartApi;
    private final CouponInternalApi couponApi;

    @Override
    @Transactional
    public OrderResponse checkout(Integer userId, OrderCheckoutRequest request) {
        // 1. Khóa Giỏ Hàng chống sửa đổi trong lúc thanh toán
        cartApi.lockCart(request.getCartId());

        // 2. Lấy các item khách đã TICK CHỌN
        List<CartItemBasicDto> items = cartItemApi.getSelectedItemsForCheckout(request.getCartId());
        if (items.isEmpty()) {
            cartApi.unlockCart(request.getCartId());
            throw new InvalidParamException("Giỏ hàng không có sản phẩm nào được chọn để thanh toán.");
        }

        // 3. Tính toán tiền tệ
        BigDecimal subTotal = BigDecimal.ZERO;
        for (CartItemBasicDto item : items) {
            subTotal = subTotal.add(item.getCurrentPrice().multiply(new BigDecimal(item.getQuantity())));
        }

        BigDecimal shippingFee = new BigDecimal("30000.00"); // TODO: Tích hợp API GiaoHàngNhanh/GHTK sau
        BigDecimal discountAmount = BigDecimal.ZERO;
        Integer couponId = null;

        // 4. Áp dụng Mã giảm giá (Nếu có)
        if (request.getCouponCode() != null && !request.getCouponCode().isBlank()) {
            discountAmount = couponApi.calculateDiscount(request.getCouponCode(), null, subTotal);
            // TODO: Resolve couponId từ code thông qua API, ở đây tớ giả lập
            // couponId = 1; 
        }

        BigDecimal totalMoney = subTotal.add(shippingFee).subtract(discountAmount);
        if (totalMoney.compareTo(BigDecimal.ZERO) < 0) totalMoney = BigDecimal.ZERO;

        // 5. Sinh mã đơn hàng (Ví dụ: ORD-20260314-0001)
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        long countToday = orderRepo.countByOrderDateBetween(startOfDay, endOfDay);
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String orderCode = "ORD-" + datePart + "-" + String.format("%04d", countToday + 1);

        // 6. Build Order Entity
        Order order = orderMapper.toEntityFromRequest(request);
        order.setUserId(userId);
        order.setOrderCode(orderCode);
        order.setSubTotal(subTotal);
        order.setShippingFee(shippingFee);
        order.setDiscountAmount(discountAmount);
        order.setTotalMoney(totalMoney);
        order.setOrderChannel(OrderChannel.ONLINE);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setCouponId(couponId);
        order.setCreatedBy(userId);

        Order savedOrder = orderRepo.save(order);

        // TODO: Ở bước tiếp theo (Bài sau), ta sẽ lưu List<CartItem> này sang bảng `order_details` và `orders_shop`

        // 7. Bắn Event xử lý Dọn rác và Thông báo
        eventPublisher.publishEvent(new OrderCreatedEvent(savedOrder.getId(), orderCode, userId, request.getCartId()));

        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getMyOrders(Integer userId, int page, int size) {
        Page<Order> pagedResult = orderRepo.findByUserIdAndIsDeleted(
                userId, 0L, PageRequest.of(page - 1, size, Sort.by("orderDate").descending())
        );
        return PageResponse.of(pagedResult.map(orderMapper::toDto));
    }

    @Override
    @Transactional
    public void cancelOrder(Integer userId, Long orderId) {
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new DataNotFoundException("Đơn hàng không tồn tại"));

        if (!order.getUserId().equals(userId)) throw new ConflictException("Không có quyền hủy đơn hàng này");

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {
            throw new ConflictException("Không thể hủy đơn hàng đã đóng gói hoặc đang giao.");
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedBy(userId);
        orderRepo.save(order);

        // TODO: Bắn Event OrderCancelledEvent để Hệ thống Inventory trả lại hàng tồn kho.
    }
}