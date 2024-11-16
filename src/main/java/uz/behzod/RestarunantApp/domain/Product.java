package uz.behzod.RestarunantApp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "barcode")
    Long barcode;

    @Column(name = "unit_id")
    Long unitId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Column(name = "unit_id", insertable = false, updatable = false)
     Unit unit;
}
