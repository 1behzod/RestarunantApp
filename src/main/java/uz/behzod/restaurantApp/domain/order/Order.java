package uz.behzod.restaurantApp.domain.order;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.enums.OrderStatus;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "order")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE order SET deleted = 'true' WHERE id=?")
public class Order extends SimpleEntity {

    @NotNull
    @Column(name = "uid", length = 50, unique = true, nullable = false)
    String uid;

    @Column(name = "order_date")
    LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "waiter_id")
    Long waiterId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id", insertable = false, updatable = false)
    User waiter;

    @NotNull
    @Column(name = "branch_id", nullable = false)
    Long branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.NEW;



}
