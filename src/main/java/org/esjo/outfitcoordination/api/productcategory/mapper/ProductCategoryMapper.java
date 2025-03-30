package org.esjo.outfitcoordination.api.productcategory.mapper;

import org.esjo.outfitcoordination.api.productcategory.controller.dto.BrandPriceDto;
import org.esjo.outfitcoordination.api.productcategory.controller.dto.CategoryWiseLowestPriceBrandDto;
import org.esjo.outfitcoordination.api.util.PriceFormatter;
import org.esjo.outfitcoordination.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PriceFormatter.class)
public interface ProductCategoryMapper {

    @Mapping(target = "brandName", source = "product.brand.name")
    @Mapping(target = "price", source = "product.price", qualifiedByName = "formatPrice")
    CategoryWiseLowestPriceBrandDto toCategoryWiseLowestPriceBrandDto(String productCategoryName, Product product);

    @Mapping(target = "brandName", source = "product.brand.name")
    @Mapping(target = "price", source = "product.price", qualifiedByName = "formatPrice")
    BrandPriceDto toBrandPriceDto(Product product);
}
