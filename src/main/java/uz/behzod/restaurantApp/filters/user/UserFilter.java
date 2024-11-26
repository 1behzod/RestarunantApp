package uz.behzod.restaurantApp.filters.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.filters.BaseFilter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilter extends BaseFilter {


    Long companyId;

    Long branchId;

    Long departmentId;

    String role;

    String status;

    List<String> statuses;

    @JsonIgnore
    List<Role> roles = new ArrayList<>();

    List<Long> branchIds;

    List<Long> departmentIds;

}
