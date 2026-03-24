// --- response/OrderResponse.java ---
package com.project.shopapp.domains.sales.dto.response;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String fullname;
    private String phoneNumber;
    private String address;
    private String status;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal totalMoney;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime orderDate;

    // TODO: Ở bài OrderShop và OrderDetail sẽ nhúng danh sách món hàng vào đây
}