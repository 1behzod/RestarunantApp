package uz.behzod.restaurantApp.domain.address;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.SimpleEntity;

@Entity
@Getter
@Setter
@Table(name = "region")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Region extends SimpleEntity {

    @Column(name = "name")
    String name;
}
