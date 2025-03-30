package org.esjo.outfitcoordination.api.product.mapper;

import org.esjo.outfitcoordination.api.product.controller.dto.ProductCreateRequest;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductIdResponse;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductResponse;
import org.esjo.outfitcoordination.api.product.controller.dto.ProductUpdateRequest;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "category")
    @Mapping(ignore = true, target = "brand")
    Product toModel(ProductCreateRequest request);

    ProductIdResponse toProductIdResponse(Long id);

    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "category")
    @Mapping(ignore = true, target = "brand")
    Product toModel(ProductUpdateRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "brandId", source = "brand.id")
    ProductResponse toProductResponse(Product product);
}
