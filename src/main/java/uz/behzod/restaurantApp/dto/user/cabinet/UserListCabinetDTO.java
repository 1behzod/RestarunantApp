package uz.behzod.restaurantApp.dto.user.cabinet;

import lombok.Getter;
import lombok.Setter;
import uz.behzod.restaurantApp.dto.base.CommonDTO;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.enums.UserStatus;

@Getter
@Setter
public class UserListCabinetDTO extends CommonDTO {

    String firstName;

    String lastName;

    String patronymic;

    String username;

    Role role;

    UserStatus status;

    CommonDTO branch;
}
