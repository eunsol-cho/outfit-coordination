package org.esjo.outfitcoordination.domain.product.mapper;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(ignore = true, target = "createdAt")
@Mapping(ignore = true, target = "createdBy")
@Mapping(ignore = true, target = "updatedAt")
@Mapping(ignore = true, target = "updatedBy")
public @interface IgnoreAuditedFields {

}
