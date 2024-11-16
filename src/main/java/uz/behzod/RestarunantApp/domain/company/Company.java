package uz.behzod.RestarunantApp.domain.company;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.address.Address;


@Entity
@Getter
@Setter
@Table(name = "company")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "brand")
    String brand;

    @NotNull
    @Column(name = "tin", unique = true, length = 14, nullable = false)
    String tin;

    @Embedded
    Address adress;


}
