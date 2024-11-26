package uz.behzod.restaurantApp.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@Table(name = "position")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE position SET deleted = 'true' WHERE id=?")
public class Position extends SimpleEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    String name;

}
