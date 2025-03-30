package org.esjo.outfitcoordination.api.product.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Schema(description = "상품 응답")
@Builder(toBuilder = true)
public record ProductResponse(
        @Schema(description = "상품 ID", example = "1")
        Long id,
        @Schema(description = "상품명", example = "흰색반팔티")
        String name,
        @Schema(description = "상품금액", example = "1000")
        BigDecimal price,
        @Schema(description = "카테고리 ID", example = "1")
        Long  categoryId,
        @Schema(description = "브랜드 ID", example = "1")
        Long brandId
) {
}
