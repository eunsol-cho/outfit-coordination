package org.esjo.outfitcoordination.domain.product.repository;

import org.esjo.outfitcoordination.domain.product.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByIdAndDeletedAtIsNull(Long id);

    List<BrandEntity> findAllByDeletedAtIsNull();
}
