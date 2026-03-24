package com.project.shopapp.domains.catalog.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantValueId implements Serializable {
    private Integer variantId;
    private Integer optionId;
}