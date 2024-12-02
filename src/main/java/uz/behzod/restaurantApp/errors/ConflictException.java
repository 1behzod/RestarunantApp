package uz.behzod.restaurantApp.errors;

import org.springframework.http.HttpStatus;

public class ConflictException extends GlobalException {

    public ConflictException(String title) {
        this(title, title);
    }

    public ConflictException(String title, String message) {
        super(title, message, HttpStatus.CONFLICT);
    }
}
