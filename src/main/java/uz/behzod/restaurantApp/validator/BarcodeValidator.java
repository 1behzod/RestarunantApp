package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.service.BaseService;

@Component
public class BarcodeValidator extends BaseService implements Validator<String> {

    @Override
    public void validate(String barcode) {
        if (barcode == null) {
            throw badRequestExceptionThrow(REQUIRED, BARCODE).get();
        }
    }
}
