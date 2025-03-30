package org.esjo.outfitcoordination.domain.product.repository;

import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.esjo.outfitcoordination.domain.product.entity.QBrandEntity;
import org.esjo.outfitcoordination.domain.product.entity.QProductCategoryEntity;
import org.esjo.outfitcoordination.domain.product.entity.QProductEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    private static final QProductEntity product = QProductEntity.productEntity;
    private static final QBrandEntity brand = QBrandEntity.brandEntity;
    private static final QProductCategoryEntity category = QProductCategoryEntity.productCategoryEntity;


    public List<ProductEntity> findMinPriceProductsPerCategory() {
        return findMinPriceProductsPerCategoryAndBrand(null);
    }

    public List<ProductEntity> findMinPriceProductsPerCategoryAndBrand(@Nullable Long brandId) {
        var minPriceByCategory = queryFactory
                .select(product.category.id, product.price.min())
                .from(product)
                .where(
                        brandId != null ? product.brand.id.eq(brandId) : null,
                        product.deletedAt.isNull()
                )
                .groupBy(product.category.id);

        return queryFactory
                .selectFrom(product)
                .innerJoin(product.brand, brand).fetchJoin()
                .innerJoin(product.category, category).fetchJoin()
                .where(
                        brandId != null ? product.brand.id.eq(brandId) : null,
                        Expressions.list(product.category.id, product.price).in(minPriceByCategory),
                        product.deletedAt.isNull()
                )
                .fetch();
    }

    public List<ProductEntity> findMinPriceProductsByCategoryId(Long categoryId) {
        return findMinOrMaxPriceProductsByCategoryId(categoryId, true);
    }

    public List<ProductEntity> findMaxPriceProductsByCategoryId(Long categoryId) {
        return findMinOrMaxPriceProductsByCategoryId(categoryId, false);
    }

    private List<ProductEntity> findMinOrMaxPriceProductsByCategoryId(Long categoryId, boolean isMin) {
        SubQueryExpression<BigDecimal> priceSubQuery = JPAExpressions
                .select(isMin ? product.price.min() : product.price.max())
                .from(product)
                .where(
                        product.category.id.eq(categoryId),
                        product.deletedAt.isNull()
                );

        return queryFactory
                        .selectFrom(product)
                        .innerJoin(product.brand, brand).fetchJoin()
                        .innerJoin(product.category, category).fetchJoin()
                        .where(
                                product.category.id.eq(categoryId),
                                product.price.eq(priceSubQuery),
                                product.deletedAt.isNull()
                        )
                        .fetch();
    }


}
