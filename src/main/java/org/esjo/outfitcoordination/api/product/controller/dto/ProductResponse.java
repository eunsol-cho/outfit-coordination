package org.esjo.outfitcoordination.api.product.controller.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Schema(description = "상품 응답")
@Builder(toBuilder = true)
public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        Long  categoryId,
        Long brandId
) {
}
