package uz.behzod.restaurantApp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum UserStatus {
    PENDING, ACTIVE, IN_ACTIVE;

}