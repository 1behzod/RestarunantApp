package uz.behzod.restaurantApp.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;
import uz.behzod.restaurantApp.enums.Role;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO extends CommonDTO {

    String firstName;

    String lastName;

    String patronymic;

    String username;

    String password;

    Role role;

    Long branchId;

    Long positionId;

    Long departmentId;

    Long companyId;
}

