package org.esjo.outfitcoordination.api.brand.mapper;

import org.esjo.outfitcoordination.api.brand.controller.dto.BrandCreateRequest;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandIdResponse;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandResponse;
import org.esjo.outfitcoordination.api.brand.controller.dto.BrandUpdateRequest;
import org.esjo.outfitcoordination.domain.product.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BrandMapper {

    @Mapping(ignore = true, target = "id")
    Brand toModel(BrandCreateRequest request);

    BrandIdResponse toBrandIdResponse(Long id);

    @Mapping(ignore = true, target = "id")
    Brand toModel(BrandUpdateRequest request);

    BrandResponse toBrandResponse(Brand brand);
}
