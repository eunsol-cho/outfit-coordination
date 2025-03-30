package org.esjo.outfitcoordination.domain.policy;

import org.esjo.outfitcoordination.domain.product.model.Product;
import org.springframework.stereotype.Component;
import java.util.Comparator;

import java.util.List;

/**
 * 브랜드명이 동일 최저가격 상품들 중 최댓값(사전순 마지막)인 상품 선택.
 */
@Component
public class MaxBrandNamePolicy implements TieBreakPolicy {

    @Override
    public Product selectOneAmongTies(List<Product> candidates) {
        return candidates.stream()
                .max(Comparator.comparing(p -> p.brand().name()))
                .orElseThrow();
    }

}