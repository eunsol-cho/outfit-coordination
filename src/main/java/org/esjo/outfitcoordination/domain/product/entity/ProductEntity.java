package org.esjo.outfitcoordination.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product")
@Getter
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductEntity extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Setter
    @Column(name = "name", nullable = false)
    @NotNull
    String name;

    @Setter
    @Column(name = "price", nullable = false)
    @NotNull @Positive
    BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    ProductCategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    BrandEntity brand;

    @Setter
    @Column(name = "deleted_at")
    Instant deletedAt;

    public void assignBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public void changeCategory(ProductCategoryEntity category) {
        this.category = category;
    }

}
