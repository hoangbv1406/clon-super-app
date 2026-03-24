// --- api/impl/OrderShopInternalApiImpl.java ---
package com.project.shopapp.domains.sales.api.impl;

import com.project.shopapp.domains.sales.api.OrderShopInternalApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderShopInternalApiImpl implements OrderShopInternalApi {

    // private final OrderShopRepository orderShopRepo;
    // private final CartItemInternalApi cartItemApi;
    // private final ShopInternalApi shopApi; // Để lấy Commission Rate của Shop

    @Override
    @Transactional
    public List<Integer> splitOrderToShops(Long parentOrderId, Integer userId) {
        List<Integer> createdIds = new ArrayList<>();

        // LUỒNG LOGIC (Mô phỏng do liên kết chéo bảng cart_items & order_details ở bài sau):
        // 1. Lấy danh sách CartItem đã chọn.
        // 2. Group by `shopId` (Thông qua Product của CartItem).
        // 3. Với mỗi group (mỗi Shop):
        //    - Tính SubTotal của group.
        //    - Tính Phí sàn (admin_commission = SubTotal * shop.CommissionRate).
        //    - Tính Thực nhận (shop_income = SubTotal - admin_commission).
        //    - Tạo Entity `OrderShop` (Status = PENDING).
        //    - Sinh `orderShopCode` (VD: PKG-12345).
        //    - Save `OrderShop` -> add ID vào createdIds.
        // 4. Tạo các dòng `order_details` trỏ về `OrderShop` vừa tạo.
        // 5. Trả về List ID.

        return createdIds;
    }
}