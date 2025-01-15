package uz.behzod.restaurantApp.validator;

public interface Validator<T>  {

    void validate(T object);
}
