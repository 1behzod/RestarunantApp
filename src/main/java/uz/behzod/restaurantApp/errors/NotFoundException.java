package uz.behzod.restaurantApp.errors;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GlobalException {
    //404 field not found

    public NotFoundException(String title) {
        this(title, null);
    }

    public NotFoundException(String title, String message) {
        super(title, message, HttpStatus.NOT_FOUND);
    }

}
