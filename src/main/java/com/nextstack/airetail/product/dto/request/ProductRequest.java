package com.nextstack.airetail.product.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request DTO for creating or updating a product.
 */
@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "SKU is required")
    @Size(max = 100)
    private String sku;

    @NotBlank(message = "Product name is required")
    @Size(max = 300)
    private String name;

    @Size(max = 2000)
    private String description;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal costPrice;

    @NotNull(message = "Selling price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal sellingPrice;

    @Size(max = 100)
    private String barcode;

    @Size(max = 50)
    private String unit;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    private Long categoryId;

    private Long brandId;
}
