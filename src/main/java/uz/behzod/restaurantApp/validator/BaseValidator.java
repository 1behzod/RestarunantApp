package uz.behzod.restaurantApp.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import uz.behzod.restaurantApp.constants.MessageConstants;
import uz.behzod.restaurantApp.errors.BadRequestException;
import uz.behzod.restaurantApp.errors.ConflictException;

import java.util.Collection;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class BaseValidator implements MessageConstants {

    ValidationMessage validationMessage;

    protected void validateRequiredField(Object value, String field) {
        if (value == null || (value instanceof String && ((String) value).isEmpty()) || (value instanceof Number && ((Number) value).intValue() == 0) || (value instanceof Collection<?> && CollectionUtils.isEmpty((Collection<?>) value))) {
            throw new BadRequestException(validationMessage.localize(REQUIRED, new Object[]{validationMessage.localize(field, field)}));
        }
    }

    protected void validateExistsInDatabase(boolean exists, String entity, String field, String value) {
        if (exists) {
            throw new ConflictException(validationMessage.localize(ENTITY_ALREADY_EXISTS_WITH, validationMessage.localize(entity, entity), validationMessage.localize(field, field), value));
        }
    }
}
