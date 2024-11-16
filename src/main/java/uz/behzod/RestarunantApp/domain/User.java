package uz.behzod.RestarunantApp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.enums.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(name = "first_name")
    String firstName;


    @Column(name = "last_name")
    String lastName;


    @Column(name = "middle_name")
    String middleName;


    @Column(name = "username")
    String username;


    @Column(name = "password")
    String password;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    Role role;


    @Column(name = "position_id", nullable = false)
    Long positionId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Column(name = "position_id", insertable = false, updatable = false)
    Position position;


    @Column(name = "branch_id", nullable = false)
    Long branchId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Column(name = "brach_id", insertable = false, updatable = false)
    Branch branch;


    @Column(name = "company_id", nullable = false)
    Long companyId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Column(name = "company_id", insertable = false, updatable = false)
    Company company;

}

