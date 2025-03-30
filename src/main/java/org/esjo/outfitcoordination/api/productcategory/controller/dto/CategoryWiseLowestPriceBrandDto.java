package org.esjo.outfitcoordination.api.productcategory.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "단일 카테고리에 대한 최저가 브랜드 및 상품 가격 정보를 담는 DTO")
@Builder(toBuilder = true)
public record CategoryWiseLowestPriceBrandDto(

        @Schema(description = "카테고리 이름 (예: 상의, 바지, 아우터 등)", example = "상의")
        String productCategoryName,

        @Schema(description = "브랜드 이름", example = "C")
        String brandName,

        @Schema(description = "해당 카테고리에서의 최저 가격 (원화 표기를 문자열로)", example = "10,000")
        String price
) {
}