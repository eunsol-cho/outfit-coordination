package org.esjo.outfitcoordination.domain.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditedEntity {

    @Builder.Default
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    String createdBy = "SYSTEM";

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @Builder.Default
    @LastModifiedBy
    @Column(name = "updated_by", nullable = false)
    String updatedBy = "SYSTEM";

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    Instant updatedAt;

}
