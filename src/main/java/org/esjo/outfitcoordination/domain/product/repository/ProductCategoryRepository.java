package org.esjo.outfitcoordination.domain.product.repository;

import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    Optional<ProductCategoryEntity> findByDisplayName(String displayName);

    Optional<ProductCategoryEntity> findByIdAndDeletedAtIsNull(Long id);
}
