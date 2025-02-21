package uz.behzod.restaurantApp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Role {
    SUPER_ADMIN,
    ADMIN,
    WAITER;

    private final String name;
    private final String code;

    Role() {
        this.code = this.name();
        this.name = this.getName();
    }
    }
