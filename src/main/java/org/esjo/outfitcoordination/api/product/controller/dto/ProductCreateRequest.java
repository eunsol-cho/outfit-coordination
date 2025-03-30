package org.esjo.outfitcoordination.api.product.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Schema(description = "상품 생성 요청")
@Builder(toBuilder = true)
public record ProductCreateRequest(
        @NotBlank(message = "상품명을 입력해주세요.")
        @Size(max = 100, message = "상품명은 100자 이하로 입력해주세요.")
        @Schema(description = "상품명", example = "흰색반팔티")
        String name,

        @Schema(description = "상품금액", example = "1000")
        @NotNull @Positive
        BigDecimal price,

        @Schema(description = "카테고리 아이디", example = "1")
        @NotNull
        Long categoryId
) {

}
