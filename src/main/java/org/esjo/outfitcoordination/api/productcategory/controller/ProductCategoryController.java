package org.esjo.outfitcoordination.api.productcategory.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.api.productcategory.controller.dto.CategoryWiseLowestPriceBrandResponse;
import org.esjo.outfitcoordination.api.productcategory.controller.dto.CategoryWisePriceRangeBrandResponse;
import org.esjo.outfitcoordination.api.productcategory.service.ProductCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 카테고리 API")
@RestController
@RequestMapping("v1/product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService service;

    @Operation(summary = "(구현 1) 카테고리별 최저가 브랜드 및 상품 가격 조회")
    @GetMapping("/lowest-price-brands")
    public CategoryWiseLowestPriceBrandResponse getCategoryWiseLowestPriceBrand() {
        return service.getCategoryWiseLowestPriceBrand();
    }

    @Operation(summary = "(구현 3) 상품 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회")
    @GetMapping("/{productCategoryName}/price-range-brands")
    public CategoryWisePriceRangeBrandResponse getCategoryWisePriceRangeBrands(
            @Parameter(description = "상품 카테고리 이름", example = "상의")
            @PathVariable String productCategoryName
    ) {
        return service.getCategoryWisePriceRangeBrands(productCategoryName);
    }

}
