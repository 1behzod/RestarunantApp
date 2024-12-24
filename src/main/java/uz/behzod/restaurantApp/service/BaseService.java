package uz.behzod.restaurantApp.service;

import com.fasterxml.jackson.databind.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.constants.MessageKeys;
import uz.behzod.restaurantApp.errors.BadRequestException;
import uz.behzod.restaurantApp.errors.ConflictException;
import uz.behzod.restaurantApp.errors.NotFoundException;
import uz.behzod.restaurantApp.errors.UnauthorizedException;

import java.util.function.Supplier;

@Component
@Slf4j
public class BaseService {


    protected static Supplier<NotFoundException> notFoundExceptionThrow(String title, String message) {
        return () -> new NotFoundException(title, message);
    }

    protected static Supplier<NotFoundException> notFoundExceptionThrow(String message) {
        return () -> new NotFoundException(message);
    }

    protected static Supplier<BadRequestException> badRequestExceptionThrow(String message) {


        return () -> new BadRequestException(message);
    }

    protected static Supplier<ConflictException> conflictExceptionThrow(String message) {
        return () -> new ConflictException(message);
    }

    protected static Supplier<UnauthorizedException> unauthorizedExceptionThrow(String message) {
        return () -> new UnauthorizedException(message);
    }
}
