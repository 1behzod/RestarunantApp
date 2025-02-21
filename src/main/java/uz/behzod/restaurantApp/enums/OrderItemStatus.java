package uz.behzod.restaurantApp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum OrderItemStatus {
    NEW,
    PREPARING,
    READY,
    GIVEN,
    CANCELLED,
}
