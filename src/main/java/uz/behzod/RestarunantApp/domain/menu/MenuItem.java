package uz.behzod.RestarunantApp.domain.menu;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.product.Product;
import uz.behzod.RestarunantApp.domain.unit.Unit;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "menu_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "product_id", nullable = false)
    Long productId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;

    @NotNull
    @Column(name = "price", nullable = false)
    BigDecimal price;

    @NotNull
    @Column(name = "unit_id", nullable = false)
    Long unitId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;
}
