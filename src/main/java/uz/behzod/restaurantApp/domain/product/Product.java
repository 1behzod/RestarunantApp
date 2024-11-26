package uz.behzod.restaurantApp.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.unit.Unit;

@Entity
@Getter
@Setter
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE product SET deleted = 'true' WHERE id=?")
public class Product extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "barcode")
    String barcode;

    @Column(name = "unit_id")
    Long unitId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;

    @NotNull
    @Column(name = "department_id", nullable = false)
    Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    Department department;
}
