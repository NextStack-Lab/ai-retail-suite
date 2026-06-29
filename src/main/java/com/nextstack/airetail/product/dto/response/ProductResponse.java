package com.nextstack.airetail.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for product data.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private String barcode;
    private String unit;
    private Long companyId;
    private String companyName;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;
}
