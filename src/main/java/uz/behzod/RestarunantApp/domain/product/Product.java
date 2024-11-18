package uz.behzod.RestarunantApp.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.metamodel.mapping.SqlTypedMapping;
import uz.behzod.RestarunantApp.domain.SimpleEntity;
import uz.behzod.RestarunantApp.domain.unit.Unit;

@Entity
@Getter
@Setter
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "barcode")
    String barcode;

    @NotNull
    @Column(name = "unit_id", nullable = false)
    Long unitId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;
}
