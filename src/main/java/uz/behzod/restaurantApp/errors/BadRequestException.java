package uz.behzod.restaurantApp.errors;

import org.springframework.http.HttpStatus;

public class BadRequestException extends GlobalException {

    public BadRequestException(String title) {
        this(title, null);
    }

    @Override
    public HttpStatus getStatus() {
        return super.getStatus();
    }

    public BadRequestException(String title, String message) {
        super(title, message, HttpStatus.BAD_REQUEST);
    }
}
