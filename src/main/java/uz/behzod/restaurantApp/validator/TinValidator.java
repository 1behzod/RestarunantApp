package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class TinValidator extends BaseService implements Validator<String> {

    @Override
    public void validate(String tin) {
        if (!StringUtils.hasLength(tin)) {
            throw badRequestExceptionThrow(REQUIRED, TIN).get();
        }
    }
}
