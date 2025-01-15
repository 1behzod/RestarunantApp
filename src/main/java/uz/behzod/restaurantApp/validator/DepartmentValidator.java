package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class DepartmentValidator extends BaseService implements Validator<Long> {

    @Override
    public void validate(Long id) {
        if (id == null) {
            throw badRequestExceptionThrow(REQUIRED, DEPARTMENT).get();
        }
    }
}
