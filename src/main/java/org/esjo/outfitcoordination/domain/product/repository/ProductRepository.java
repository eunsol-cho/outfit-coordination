package org.esjo.outfitcoordination.domain.product.repository;

import org.esjo.outfitcoordination.domain.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByIdAndDeletedAtIsNull(Long id);

    @Modifying
    @Query("UPDATE ProductEntity p SET p.deletedAt = :deletedAt WHERE p.brand.id = :brandId AND p.deletedAt IS NULL")
    int deleteAllByBrandId(@Param("brandId") Long brandId, @Param("deletedAt") java.time.Instant deletedAt);

    @Query("""
        SELECT p
        FROM ProductEntity p
        JOIN FETCH p.category c
        JOIN FETCH p.brand b
        WHERE p.brand.id = :brandId
          AND p.deletedAt IS NULL
    """)
    List<ProductEntity> findAllByBrandIdFetchJoin(@Param("brandId") Long brandId);

    interface BrandPriceProjection {
        Long getBrandId();
        BigDecimal getTotalPrice();
    }

    @Query(value = """
        SELECT sub.brand_id AS brandId,
               SUM(sub.min_price) AS totalPrice
        FROM (
            SELECT p.brand_id, p.category_id, MIN(p.price) AS min_price
            FROM product p
            WHERE p.deleted_at IS NULL
            GROUP BY p.brand_id, p.category_id
        ) sub
        GROUP BY sub.brand_id
        HAVING COUNT(DISTINCT sub.category_id) = (SELECT COUNT(*) FROM product_category)
        ORDER BY totalPrice
        LIMIT 1
    """, nativeQuery = true)
    Optional<BrandPriceProjection> findCheapestBrandCoveringAllCategories();

}
