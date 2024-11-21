package uz.behzod.restaurantApp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class SimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "deleted", nullable = false, columnDefinition = "boolean default false")
    boolean deleted;

    public String getName() {
        return null;
    }

    public CommonDTO toCommonDTO() {
        return new CommonDTO(getId(), getName());
    }
}
