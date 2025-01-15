package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class UsernameValidator extends BaseService implements Validator<String> {

    @Override
    public void validate(String username) {
        if (!StringUtils.hasLength(username)) {
            throw badRequestExceptionThrow(REQUIRED, USERNAME).get();
        }
    }
}
