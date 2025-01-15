package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class CompanyValidator extends BaseService implements Validator<Long> {

    @Override
    public void validate(Long id) {
        if (id == null) {
            throw badRequestExceptionThrow(REQUIRED, COMPANY).get();
        }
    }
}
