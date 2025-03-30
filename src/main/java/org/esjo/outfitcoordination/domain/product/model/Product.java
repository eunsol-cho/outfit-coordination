package org.esjo.outfitcoordination.domain.product.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record Product(
        Long id,
        String name,
        BigDecimal price,
        ProductCategory category,
        Brand brand
) {
}
