package uz.behzod.restaurantApp.filters.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemFilter extends BaseFilter {

    Long menuId;
}
