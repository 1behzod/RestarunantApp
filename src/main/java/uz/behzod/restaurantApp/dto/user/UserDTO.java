package uz.behzod.restaurantApp.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;
import uz.behzod.restaurantApp.enums.UserStatus;

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

    Long branchId;

    Long companyId;

    String role;

    Long positionId;
}

