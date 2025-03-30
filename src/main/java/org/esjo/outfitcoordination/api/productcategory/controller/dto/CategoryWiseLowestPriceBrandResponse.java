package org.esjo.outfitcoordination.api.productcategory.controller.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "상품 카테고리별 최저가격 브랜드와 상품 가격 정보를 담는 응답")
@Builder(toBuilder = true)
public record CategoryWiseLowestPriceBrandResponse(

        @ArraySchema(schema = @Schema(description = "카테고리별 최저가격 정보 목록"))
        List<CategoryWiseLowestPriceBrandDto> items,

        @Schema(description = "해당 카테고리들의 최저가격 상품을 모두 합산한 총액", example = "34,100")
        String totalPrice
) {
}