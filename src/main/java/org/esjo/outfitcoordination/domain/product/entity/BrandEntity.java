package org.esjo.outfitcoordination.domain.product.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "brand")
@Getter
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    @Column(name = "name", nullable = false)
    String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    List<ProductEntity> products;

    @Setter
    @Column(name = "deleted_at")
    Instant deletedAt;

}
