package uz.behzod.RestarunantApp.domain.menu;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.SimpleEntity;
import uz.behzod.RestarunantApp.domain.product.Product;
import uz.behzod.RestarunantApp.domain.unit.Unit;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "menu_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItem extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "product_id", nullable = false)
    Long productId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

    @Column(name = "price", scale = 2, precision = 14)
    BigDecimal price;

    @NotNull
    @Column(name = "unit_id", nullable = false)
    Long unitId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;

    @NotNull
    @Column(name = "menu_id", nullable = false)
    Long menuId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", insertable = false, updatable = false)
    Menu menu;
}
