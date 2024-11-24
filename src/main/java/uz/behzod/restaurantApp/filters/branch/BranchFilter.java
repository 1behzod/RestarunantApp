package uz.behzod.restaurantApp.filters.branch;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchFilter extends BaseFilter {

    Long companyId;
}
