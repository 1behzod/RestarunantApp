package uz.behzod.restaurantApp.validator;

public interface ValidatorStrategy<T> {

    void validate(T dto);
}
