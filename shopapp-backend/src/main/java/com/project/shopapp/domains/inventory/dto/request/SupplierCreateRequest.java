package com.project.shopapp.domains.inventory.dto.request;
import com.project.shopapp.domains.inventory.validation.ValidTaxCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierCreateRequest {
    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    private String name;

    @Email(message = "Email không đúng định dạng")
    private String contactEmail;

    private String contactPhone;

    @ValidTaxCode
    private String taxCode;

    private String address;
}