package org.esjo.outfitcoordination.domain.product.mapper;

import org.esjo.outfitcoordination.domain.product.entity.BrandEntity;
import org.esjo.outfitcoordination.domain.product.model.Brand;
import org.mapstruct.*;

@Mapper
public interface BrandEntityMapper {
    Brand toModel(BrandEntity brandEntity);

    @IgnoreAuditedFields
    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "products")
    @Mapping(ignore = true, target = "deletedAt")
    BrandEntity toEntity(Brand brand);

    @IgnoreAuditedFields
    @Mapping(ignore = true, target = "products")
    @Mapping(ignore = true, target = "deletedAt")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(@MappingTarget BrandEntity target, Brand source);
}
