package uz.behzod.restaurantApp.domain.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.SQLDelete;
import uz.behzod.restaurantApp.constants.Constants;
import uz.behzod.restaurantApp.domain.Department;
import uz.behzod.restaurantApp.domain.Position;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.enums.UserStatus;

@Entity
@Getter
@Setter
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE users SET deleted = 'true' WHERE id=?")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends SimpleEntity {

    @Size(max = 40)
    @Column(name = "first_name", length = 40)
    String firstName;

    @Size(max = 40)
    @Column(name = "last_name", length = 40)
    String lastName;

    @Size(max = 40)
    @Column(name = "patronymic", length = 40)
    String patronymic;

    @NotNull
    @Pattern(regexp = Constants.USERNAME)
    @Column(name = "username", nullable = false)
    String username;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    UserStatus status = UserStatus.PENDING;

    @Column(name = "position_id", nullable = false)
    Long positionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position_id", insertable = false, updatable = false)
    Position position;

    @Column(name = "department_id")
    Long departmentId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id", insertable = false, updatable = false)
    Department department;

    @Column(name = "branch_id")
    Long branchId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "branch_id", insertable = false, updatable = false)
    Branch branch;

    @Column(name = "company_id", nullable = false)
    Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    Company company;

    @Column(name = "token_id")
    Long tokenId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "token_id", insertable = false, updatable = false)
    UserToken token;
}

