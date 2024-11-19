package uz.behzod.restaurantApp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.branch.Branch;

@Entity
@Getter
@Setter
@Table(name = "department")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "branch_id", nullable = false)
    Long branchId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

}
