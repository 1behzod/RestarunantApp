package uz.behzod.restaurantApp.constants;

public interface MessageKeys {

    String ENTITY_NOT_FOUND = "exception.{entity}.not_found";
    String ENTITY_REQUIRED_FIELD = "exception.{entity}.required.{field}";
    String ENTITY_ALREADY_EXISTS_FIELD = "exception.{entity}.already_exists.{field}";
    String VALIDATION_REQUIRED_FIELD = "validation.required.{field}";
    String GENERAL_UNAUTHORIZED = "exception.unauthorized";
    String GENERAL_BAD_REQUEST = "exception.bad_request";
    String GENERAL_CONFLICT = "exception.conflict";
    String GENERAL_INTERNAL_ERROR = "exception.internal_error";
}
