package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class PinflValidator extends BaseService implements Validator<String> {

    @Override
    public void validate(String pinfl) {
        if (!StringUtils.hasLength(pinfl)) {
            throw badRequestExceptionThrow(REQUIRED, PINFL).get();
        }
    }
}
