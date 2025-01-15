package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class MenuItemValidator extends BaseService implements Validator<Long> {

    @Override
    public void validate(Long id) {
        if (id == null) {
            throw badRequestExceptionThrow(REQUIRED, MENU_ITEM).get();
        }
    }
}
