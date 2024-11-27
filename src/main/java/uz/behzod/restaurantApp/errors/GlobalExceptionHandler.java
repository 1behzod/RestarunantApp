package uz.behzod.restaurantApp.errors;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage(), e);
        log.info("HttpServletRequest : {}", httpServletRequest);
        return get(e.getTitle(), e.getMessage(), e.getStatus(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage(), e);
        log.info("HttpServletRequest : {}", httpServletRequest);
        return get(e.getTitle(), e.getMessage(), e.getStatus(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorDTO> handleConflictException(ConflictException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage(), e);
        log.info("HttpServletRequest : {}", httpServletRequest);
        return get(e.getTitle(), e.getMessage(), e.getStatus(), httpServletRequest.getRequestURI());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> handleUnauthorizedException(UnauthorizedException e, HttpServletRequest httpServletRequest) {
        log.error(e.getMessage(), e);
        log.info("HttpServletRequest : {}", httpServletRequest);
        return get(e.getTitle(), e.getMessage(), e.getStatus(), httpServletRequest.getRequestURI());
    }

    private static ResponseEntity<ErrorDTO> get(String title, String message, HttpStatus status, String path) {
        return new ResponseEntity<>(
                ErrorDTO
                        .builder()
                        .title(title)
                        .detail(message)
                        .status(status.value())
                        .path(path)
                        .timestamp(LocalDateTime.now().toString())
                        .build(),
                status
        );
    }
}
