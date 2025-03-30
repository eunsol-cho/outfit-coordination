package org.esjo.outfitcoordination.domain.product.mapper;

import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.mapstruct.*;

@Mapper
public interface ProductEntityMapper {

    // brand, category 정보필요시 fetch join 으로 가져오기
    @Mapping(ignore = true, target = "category")
    @Mapping(ignore = true, target = "brand")
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
