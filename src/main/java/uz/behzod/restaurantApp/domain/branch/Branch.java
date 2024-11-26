package uz.behzod.restaurantApp.domain.branch;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.address.Address;
import uz.behzod.restaurantApp.domain.company.Company;

@Entity
@Getter
@Setter
@Table(name = "branch")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE branch SET deleted = 'true' WHERE id=?")
public class Branch extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Embedded
    Address address;

    @NotNull
    @Column(name = "company_id", nullable = false)
    Long companyId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", updatable = false, insertable = false)
    Company company;
}



