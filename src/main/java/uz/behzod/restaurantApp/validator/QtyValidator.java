package uz.behzod.restaurantApp.validator;

import uz.behzod.restaurantApp.service.BaseService;

import java.math.BigDecimal;

public class QtyValidator extends BaseService implements Validator<BigDecimal> {

    @Override
    public void validate(BigDecimal qty) {
        if (qty == null || qty.compareTo(BigDecimal.ZERO) == 0) {
            throw badRequestExceptionThrow(REQUIRED, QTY).get();
        }

    }
}
