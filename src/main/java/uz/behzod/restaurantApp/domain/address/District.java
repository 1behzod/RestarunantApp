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
@Table(name = "district")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class District extends SimpleEntity {


    @Column(name = "name")
    String name;

    @Column(name = "region_id")
    Long regionId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    Region region;

}
