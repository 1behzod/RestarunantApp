package uz.behzod.RestarunantApp.domain.address;


import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {

    @Column(name = "region_id")
    Long regionId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    Region region;

    @Column(name = "district_id")
    Long districtId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", updatable = false, insertable = false)
    District district;

    @Column(name = "neighbourhood_id")
    Long neighbourhoodId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "neighbourhood_id", updatable = false, insertable = false)
    Neighbourhood neighbourhood;

    @Column(name = "street")
    String street;
}
