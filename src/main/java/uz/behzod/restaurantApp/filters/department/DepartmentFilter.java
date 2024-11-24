package uz.behzod.restaurantApp.filters.department;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentFilter extends BaseFilter {

    Long branchId;

}

