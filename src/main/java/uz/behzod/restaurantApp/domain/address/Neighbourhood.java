package uz.behzod.restaurantApp.domain.address;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.domain.SimpleEntity;

@Entity
@Getter
@Setter
@Table(name = "neighbourhood")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Neighbourhood extends SimpleEntity {

    @Column(name = "name")
    String name;

    @Column(name = "district_id")
    Long districtId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", insertable = false, updatable = false)
    District district;
}
