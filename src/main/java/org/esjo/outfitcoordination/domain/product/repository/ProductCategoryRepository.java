package org.esjo.outfitcoordination.domain.product.repository;

import org.esjo.outfitcoordination.domain.product.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

    Optional<ProductCategoryEntity> findByCodeAndDeletedAtIsNull(String code);

    Optional<ProductCategoryEntity> findByIdAndDeletedAtIsNull(Long id);

    List<ProductCategoryEntity> findAllByDeletedAtIsNull();
}
