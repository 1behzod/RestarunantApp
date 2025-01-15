package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class PasswordValidator extends BaseService implements Validator<String> {

    @Override
    public void validate(String password) {
        if (!StringUtils.hasLength(password)) {
            throw badRequestExceptionThrow(REQUIRED, PASSWORD).get();
        }
    }
}
