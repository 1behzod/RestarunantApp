package uz.behzod.restaurantApp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum PaymentStatus {

    PAID,
    NOT_PAID;

    private final String name;
    private final String code;

    PaymentStatus() {
        this.code = this.name();
        this.name = this.getName();
    }

    public static PaymentStatus getByCode(String code) {
        return Arrays.stream(PaymentStatus.values()).filter(paymentStatus -> paymentStatus.getCode().equals(code)).findFirst().orElse(null);
    }

}
