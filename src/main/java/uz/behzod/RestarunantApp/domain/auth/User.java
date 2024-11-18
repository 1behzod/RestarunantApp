package uz.behzod.RestarunantApp.domain.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.branch.Branch;
import uz.behzod.RestarunantApp.domain.company.Company;
import uz.behzod.RestarunantApp.domain.Position;
import uz.behzod.RestarunantApp.enums.Role;

@Entity
@Getter
@Setter
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(max = 40)
    @Column(name = "first_name", length = 40)
    String firstName;

    @Size(max = 40)
    @Column(name = "last_name", length = 40)
    String lastName;

    @Size(max = 40)
    @Column(name = "patronymic", length = 40)
    String patronymic;

    @Column(name = "username")
    String username;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;

    @Column(name = "position_id", nullable = false)
    Long positionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    Position position;

    @Column(name = "branch_id", nullable = false)
    Long branchId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brach_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "company_id", nullable = false)
    Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    Company company;

}

