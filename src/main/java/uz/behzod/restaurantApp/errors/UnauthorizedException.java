package uz.behzod.restaurantApp.errors;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends GlobalException {
    // 401 Unauthorized: Authentication is required or failed.

    public UnauthorizedException(String title) {
        this(title, null);
    }

    public UnauthorizedException(String title, String message) {
        super(title, message, HttpStatus.UNAUTHORIZED);
    }
}
