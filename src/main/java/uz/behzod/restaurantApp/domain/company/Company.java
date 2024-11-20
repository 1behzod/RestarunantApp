package uz.behzod.restaurantApp.domain.company;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.SimpleEntity;
import uz.behzod.restaurantApp.domain.address.Address;


@Entity
@Getter
@Setter
@Table(name = "company")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "brand")
    String brand;

    @NotNull
    @Size(min = 9, max = 9)
    @Column(name = "tin", unique = true, nullable = false)
    String tin;

    @NotNull
    @Size(min = 14, max = 14)
    @Column(name = "pinfl", unique = true, nullable = false)
    String pinfl;

    @Embedded
    Address address;
}
