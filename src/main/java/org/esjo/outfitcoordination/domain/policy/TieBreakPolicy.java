package org.esjo.outfitcoordination.domain.policy;

import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.model.Product;

import java.util.List;

/**
 * '최저가 동률' 상황에서 어떤 상품을 최종 선택할지 결정하는 정책.
 */
public interface TieBreakPolicy {
    Product selectOneAmongTies(List<Product> tiedProducts);
}
