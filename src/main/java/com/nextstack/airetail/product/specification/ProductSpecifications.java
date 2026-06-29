package com.nextstack.airetail.product.specification;

import com.nextstack.airetail.common.specification.BaseSpecifications;
import com.nextstack.airetail.product.dto.request.ProductPageRequest;
import com.nextstack.airetail.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA specifications for product queries.
 */
public final class ProductSpecifications {

    private ProductSpecifications() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Builds a specification from page request filters.
     *
     * @param request page and filter parameters
     * @return combined specification
     */
    public static Specification<Product> fromRequest(ProductPageRequest request) {
        return Specification.where(BaseSpecifications.<Product>isActive())
                .and(searchFilter(request.getSearch()))
                .and(companyIdEquals(request.getCompanyId()))
                .and(categoryIdEquals(request.getCategoryId()))
                .and(brandIdEquals(request.getBrandId()));
    }

    private static Specification<Product> searchFilter(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("sku")), pattern),
                    cb.like(cb.lower(root.get("barcode")), pattern));
        };
    }

    private static Specification<Product> companyIdEquals(Long companyId) {
        return (root, query, cb) -> {
            if (companyId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("company").get("id"), companyId);
        };
    }

    private static Specification<Product> categoryIdEquals(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    private static Specification<Product> brandIdEquals(Long brandId) {
        return (root, query, cb) -> {
            if (brandId == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("brand").get("id"), brandId);
        };
    }
}
