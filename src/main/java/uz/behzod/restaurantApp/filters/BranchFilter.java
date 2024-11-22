package uz.behzod.restaurantApp.filters;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchFilter extends BaseFilter {

    Long companyId;
}
