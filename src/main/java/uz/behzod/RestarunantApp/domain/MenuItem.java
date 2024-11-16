package uz.behzod.RestarunantApp.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "name")
    String name;


    @Column(name = "product_id", nullable = false)
    Long productId;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    Product product;


    @Column(name = "price")
    BigDecimal price;


    @Column(name = "unit_id")
    Long unitId;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    Unit unit;
}
