package uz.behzod.restaurantApp.domain.order;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.domain.unit.Unit;
import uz.behzod.restaurantApp.enums.OrderItemStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE order_item SET deleted = 'true' WHERE id=?")
public class OrderItem extends SimpleEntity {

    @NotNull
    @Column(name = "menu_item_id", nullable = false)
    Long menuItemId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_item_id", insertable = false, updatable = false)
    MenuItem menuItem;

    @NotNull
    @Column(name = "unit_id", nullable = false)
    Long unitId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    Unit unit;

    @Column(name = "price", scale = 2, precision = 25)
    BigDecimal price;

    @Column(name = "total_price", scale = 2, precision = 25)
    BigDecimal totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    OrderItemStatus status = OrderItemStatus.NEW;

    @NotNull
    @Column(name = "order_id", nullable = false)
    Long orderId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", updatable = false, insertable = false)
    Order order;

    @Column(name = "order_date")
    LocalDateTime orderDate = LocalDateTime.now();
}
