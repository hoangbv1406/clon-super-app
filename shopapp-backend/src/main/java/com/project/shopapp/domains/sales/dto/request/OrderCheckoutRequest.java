// --- request/OrderCheckoutRequest.java ---
package com.project.shopapp.domains.sales.dto.request;
import com.project.shopapp.domains.sales.validation.ValidPaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCheckoutRequest {
    @NotNull(message = "ID Giỏ hàng không được để trống")
    private Integer cartId; // Đơn hàng được build từ 1 Giỏ hàng cụ thể

    @NotBlank(message = "Họ tên không được trống")
    private String fullname;

    @NotBlank(message = "Số điện thoại không được trống")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ nhận hàng không được trống")
    private String address;

    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String note;

    @ValidPaymentMethod
    private String paymentMethod;

    private String couponCode; // Mã giảm giá của Sàn (Shopee Voucher)
}