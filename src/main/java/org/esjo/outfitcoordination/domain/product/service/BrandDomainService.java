package org.esjo.outfitcoordination.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.esjo.outfitcoordination.domain.product.mapper.BrandEntityMapper;
import org.esjo.outfitcoordination.domain.product.model.Brand;
import org.esjo.outfitcoordination.domain.product.repository.BrandRepository;
import org.esjo.outfitcoordination.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BrandDomainService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final BrandEntityMapper mapper;

    @Transactional
    public Long create(Brand brand) {
        var entity = mapper.toEntity(brand);
        return brandRepository.save(entity).getId();
    }

    @Transactional
    public void update(Long id, Brand brand) {
        var entity = brandRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("브랜드를 찾을 수 없습니다: " + id));
        mapper.merge(entity, brand);
    }

    @Transactional
    public void delete(Long id) {
        var brand = brandRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("브랜드를 찾을 수 없습니다: " + id));

        productRepository.deleteAllByBrandId(brand.getId(), Instant.now());

        brand.setDeletedAt(Instant.now());
    }

    public List<Brand> findAllByDeletedAtIsNull() {
        // TODO.esjo 커서로 조회
        var brands = brandRepository.findAllByDeletedAtIsNull();
        if (brands.isEmpty()) {
            throw new NoSuchElementException("브랜드가 존재하지 않습니다.");
        }

        return brands.stream()
                .map(mapper::toModel)
                .toList();

    }
}
