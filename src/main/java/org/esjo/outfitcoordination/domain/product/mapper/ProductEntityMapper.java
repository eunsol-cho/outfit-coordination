package org.esjo.outfitcoordination.domain.product.mapper;

import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.mapstruct.*;

@Mapper
public interface ProductEntityMapper {
    Product toModel(ProductEntity productEntity);

    @IgnoreAuditedFields
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "category")
    @Mapping(ignore = true, target = "brand")
    @Mapping(ignore = true, target = "deletedAt")
    ProductEntity toEntity(Product product);

    @IgnoreAuditedFields
    @Mapping(ignore = true, target = "category")
    @Mapping(ignore = true, target = "brand")
    @Mapping(ignore = true, target = "deletedAt")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(@MappingTarget ProductEntity target, Product source);
}
