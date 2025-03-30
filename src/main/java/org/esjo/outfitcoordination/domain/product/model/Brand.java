package org.esjo.outfitcoordination.domain.product.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Brand(
        Long id,
        String name
) {
}
