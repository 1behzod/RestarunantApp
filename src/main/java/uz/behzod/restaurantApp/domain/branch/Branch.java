package uz.behzod.restaurantApp.domain.branch;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.domain.address.Address;

@Entity
@Getter
@Setter
@Table(name = "branch")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Branch extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "company_id", nullable = false)
    Long companyId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", updatable = false, insertable = false)
    Company company;

    @Embedded
    Address address;

}



