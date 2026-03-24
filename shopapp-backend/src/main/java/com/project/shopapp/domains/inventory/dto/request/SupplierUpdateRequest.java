package com.project.shopapp.domains.inventory.dto.request;
import com.project.shopapp.domains.inventory.validation.ValidSupplierStatus;
import com.project.shopapp.domains.inventory.validation.ValidTaxCode;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SupplierUpdateRequest {
    private String name;

    @Email(message = "Email không đúng định dạng")
    private String contactEmail;

    private String contactPhone;

    @ValidTaxCode
    private String taxCode;

    private String address;

    @ValidSupplierStatus
    private String status;
}