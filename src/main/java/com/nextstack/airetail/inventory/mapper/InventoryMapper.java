package com.nextstack.airetail.inventory.mapper;

import com.nextstack.airetail.inventory.dto.request.InventoryRequest;
import com.nextstack.airetail.inventory.dto.response.InventoryResponse;
import com.nextstack.airetail.inventory.entity.Inventory;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;

/**
 * MapStruct mapper for {@link Inventory} entity and DTOs.
 */
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface InventoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "active", ignore = true)
    Inventory toEntity(InventoryRequest request);

    @Mapping(source = "branch.id", target = "branchId")
    @Mapping(source = "branch.name", target = "branchName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.sku", target = "productSku")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(target = "lowStock", expression = "java(isLowStock(entity))")
    InventoryResponse toResponse(Inventory entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "branch", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateEntity(InventoryRequest request, @MappingTarget Inventory entity);

    default boolean isLowStock(Inventory entity) {
        if (entity.getReorderLevel() == null) {
            return false;
        }
        return entity.getQuantity().compareTo(entity.getReorderLevel()) <= 0;
    }
}
