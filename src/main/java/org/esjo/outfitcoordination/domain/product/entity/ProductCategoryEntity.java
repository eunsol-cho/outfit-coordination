package org.esjo.outfitcoordination.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "product_category")
@Getter
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCategoryEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "code", nullable = false)
    @NotNull
    String code;

    @Column(name = "display_name", nullable = false)
    @NotNull
    String displayName;

    @Column(name = "display_order", nullable = false)
    @NotNull
    Integer displayOrder;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<ProductEntity> products;

    @Column(name = "deleted_at")
    Instant deletedAt;

}

