package com.project.shopapp.domains.sales.repository;

import com.project.shopapp.domains.sales.entity.OrderDetail;
import com.project.shopapp.domains.sales.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>, JpaSpecificationExecutor<OrderDetail> {

    // Lấy chi tiết của 1 Đơn hàng tổng
    List<OrderDetail> findByOrderId(Long orderId);

    // Lấy chi tiết của 1 Kiện hàng (Shop con)
    List<OrderDetail> findByOrderShopId(Integer orderShopId);

    // [NGHIỆP VỤ KẾ TOÁN]: Lấy danh sách hàng hóa chưa thanh toán cho 1 Supplier
    @Query("SELECT od FROM OrderDetail od JOIN od.orderShop os " +
            "WHERE od.supplierId = :supplierId AND od.isSettled = false " +
            "AND os.status = 'DELIVERED'")
    List<OrderDetail> findUnsettledItemsBySupplier(Integer supplierId);

    // Kế toán duyệt trả tiền loạt cho Supplier
    @Modifying
    @Query("UPDATE OrderDetail od SET od.isSettled = true, od.settlementDate = CURRENT_TIMESTAMP, " +
            "od.settlementRef = :ref, od.settlementNote = :note " +
            "WHERE od.id IN :detailIds")
    int settleSupplierItems(List<Integer> detailIds, String ref, String note);

    // Cập nhật trạng thái đổi trả 1 phần
    @Modifying
    @Query("UPDATE OrderDetail od SET od.itemStatus = :status WHERE od.id = :detailId")
    void updateItemStatus(Integer detailId, OrderItemStatus status);
}