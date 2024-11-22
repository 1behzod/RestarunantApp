package uz.behzod.restaurantApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    String tin;

    String pinfl;

    String firstName;

    String lastName;

    String patronymic;

    String username;

    String password;

    String phone;

    Long branchId;

    Long companyId;

    String role;
}

