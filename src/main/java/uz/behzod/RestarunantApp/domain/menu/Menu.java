package uz.behzod.RestarunantApp.domain.menu;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.SimpleEntity;
import uz.behzod.RestarunantApp.domain.branch.Branch;

@Entity
@Getter
@Setter
@Table(name = "menu")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Menu extends SimpleEntity {

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
