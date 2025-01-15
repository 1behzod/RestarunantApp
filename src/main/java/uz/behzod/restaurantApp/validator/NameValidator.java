package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class NameValidator extends BaseService implements Validator<String> {

    @Override
    public void validate(String name) {
        if (!StringUtils.hasLength(name)) {
            throw badRequestExceptionThrow(REQUIRED, NAME).get();
        }
    }
}
