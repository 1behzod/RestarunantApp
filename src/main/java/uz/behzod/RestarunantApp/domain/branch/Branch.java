package uz.behzod.RestarunantApp.domain.branch;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.company.Company;
import uz.behzod.RestarunantApp.domain.address.Address;

@Entity
@Getter
@Setter
@Table(name = "branch")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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
    Address adress;

}



