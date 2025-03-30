package org.esjo.outfitcoordination.domain.product.mapper;

import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.esjo.outfitcoordination.domain.product.model.ProductCategory;
import org.mapstruct.Mapper;

@Mapper
public interface ProductCategoryEntityMapper {
    ProductCategory toModel(ProductCategoryEntity productCategoryEntity);
}
