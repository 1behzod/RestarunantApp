package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.service.BaseService;

import java.math.BigDecimal;

@Component
public class PriceValidator extends BaseService implements Validator<BigDecimal> {

    @Override
    public void validate(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw badRequestExceptionThrow(REQUIRED, PRICE).get();
        }
    }
}
