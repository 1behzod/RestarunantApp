package uz.behzod.restaurantApp.validator;

import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.dto.user.UserDTO;

@Component
public class UserValidator extends BaseValidator implements ValidatorStrategy<UserDTO>{

    public UserValidator(ValidationMessage validationMessage) {
        super(validationMessage);
    }

    @Override
    public void validate(UserDTO dto) {
        validateRequiredField(dto.getFirstName(), NAME);
        validateRequiredField(dto.getUsername(), USERNAME);
        validateRequiredField(dto.getPassword(), PASSWORD);
        validateRequiredField(dto.getBranchId(), BRANCH);
        validateRequiredField(dto.getRole(), ROLE);
        validateRequiredField(dto.getDepartmentId(), DEPARTMENT);
        validateRequiredField(dto.getPositionId(), POSITION);
        validateRequiredField(dto.getCompanyId(), COMPANY);
    }
}
