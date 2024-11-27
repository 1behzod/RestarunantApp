package uz.behzod.restaurantApp.errors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ErrorDTO implements Serializable {

    String title;

    String detail;

    String path;

    String message;

    int status;

    String timestamp;
}
