package uz.behzod.RestarunantApp.domain;


import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Adress {


    @Column(name = "region_id")
    Long regionId;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", updatable = false, insertable = false)
    Region region;


    @Column(name = "district_id")
    Long districtId;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "distinct_id", updatable = false, insertable = false)
    District district;


    @Column(name = "neighbour_id")
    Long neighbourhoodId;


    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "neighbour_id", updatable = false, insertable = false)
    Neighbour neighbour;


    @Column(name = "street")
    String street;
}
