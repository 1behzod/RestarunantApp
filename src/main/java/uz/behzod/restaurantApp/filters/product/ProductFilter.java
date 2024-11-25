package uz.behzod.restaurantApp.filters.product;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFilter extends BaseFilter {

    String barcode;

    Long departmentId;
}
