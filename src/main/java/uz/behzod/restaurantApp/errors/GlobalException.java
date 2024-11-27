package uz.behzod.restaurantApp.errors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@Setter
public class GlobalException extends RuntimeException {

    protected String title;
    protected HttpStatus status;

    public GlobalException(String title, String message, HttpStatus status) {
        super(Optional.ofNullable(message).orElse(title));
        this.title = title;
        this.status = status;
    }
}
