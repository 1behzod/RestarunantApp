package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class RoleValidator extends BaseService implements Validator<Role> {

    @Override
    public void validate(Role role) {
        if (role == null) {
            throw badRequestExceptionThrow(REQUIRED, ROLE).get();
        }
    }
}
