package uz.behzod.restaurantApp.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE, PENDING, IN_ACTIVE;

    private final String name;
    private final String code;

    UserStatus() {
        this.name = getName();
        this.code = name();
    }
}
