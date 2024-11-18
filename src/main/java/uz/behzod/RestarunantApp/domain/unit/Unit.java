package uz.behzod.RestarunantApp.domain.unit;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.RestarunantApp.domain.SimpleEntity;

@Entity
@Getter
@Setter
@Table(name = "unit")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Unit extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

    @NotNull
    @Column(name = "code", nullable = false)
    String code;
}
