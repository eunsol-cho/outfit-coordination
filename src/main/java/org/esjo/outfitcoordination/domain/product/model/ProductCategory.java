package org.esjo.outfitcoordination.domain.product.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProductCategory(
        Long id,
        String code,
        String displayName,
        Integer displayOrder
) {
}
