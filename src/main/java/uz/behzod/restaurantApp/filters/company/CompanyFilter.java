package uz.behzod.restaurantApp.filters.company;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyFilter extends BaseFilter {

    Long regionId;

    Long districtId;

    Long neighborhoodId;

}
