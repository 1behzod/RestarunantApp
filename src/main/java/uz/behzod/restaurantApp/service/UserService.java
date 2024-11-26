package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.user.UserDTO;
import uz.behzod.restaurantApp.dto.user.UserDetailDTO;
import uz.behzod.restaurantApp.dto.user.UserListDTO;
import uz.behzod.restaurantApp.enums.UserStatus;
import uz.behzod.restaurantApp.filters.user.UserFilter;
import uz.behzod.restaurantApp.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    private void validate(UserDTO userDTO) {
        if (!StringUtils.hasLength(userDTO.getFirstName())) {
            throw new RuntimeException("Name is required");
        }
        if (!StringUtils.hasLength(userDTO.getUsername())) {
            throw new RuntimeException("Username is required");
        }
        if (!StringUtils.hasLength(userDTO.getPassword())) {
            throw new RuntimeException("Password is required");
        }
        if (userDTO.getBranchId() == null) {
            throw new RuntimeException("BranchId is required");
        }
        if (userDTO.getRole() == null) {
            throw new RuntimeException("Role is required");
        }
        if (userDTO.getDepartmentId() == null) {
            throw new RuntimeException("DepartmentId is required");
        }
        if (userDTO.getPositionId() == null) {
            throw new RuntimeException("PositionId is required");
        }
        if (userDTO.getCompanyId() == null) {
            throw new RuntimeException("CompanyId is required");
        }
       /* if (userDTO.getId() == null) {
            if (userRepository.findByUsernameAndDeletedIsFalse((userDTO.getUsername()))) {
                throw new RuntimeException("Username already exists");
            }
        }
        if (userDTO.getId() != null) {
            if (userRepository.findByUsernameAndDeletedIsFalseAndIdNot(userDTO.getUsername(), userDTO.getId())) {
                throw new RuntimeException("Username already exists");
            }
        }*/

    }

    @Transactional
    public Long create(UserDTO userDTO) {
        this.validate(userDTO);
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setBranchId(userDTO.getBranchId());
        user.setRole(userDTO.getRole());
        user.setDepartmentId(userDTO.getDepartmentId());
        user.setPositionId(userDTO.getPositionId());
        user.setCompanyId(userDTO.getCompanyId());
        return userRepository.save(user).getId();
    }

    @Transactional
    public Long update(Long id, UserDTO userDTO) {
        userDTO.setId(id);
        this.validate(userDTO);
        return userRepository.findById(id).map(user -> {
            user.setLastName(userDTO.getLastName());
            user.setPatronymic(userDTO.getPatronymic());
            user.setUsername(userDTO.getUsername());
            user.setRole(userDTO.getRole());
            user.setPositionId(userDTO.getPositionId());
            user.setDepartmentId(userDTO.getDepartmentId());
            user.setBranchId(userDTO.getBranchId());
            return userRepository.save(user).getId();
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }

    public UserDetailDTO get(Long id) {
        return userRepository.findById(id).map(user -> {
            UserDetailDTO userDetailDTO = new UserDetailDTO();
            userDetailDTO.setId(user.getId());
            userDetailDTO.setFirstName(user.getFirstName());
            userDetailDTO.setLastName(user.getLastName());
            userDetailDTO.setPatronymic(user.getPatronymic());
            userDetailDTO.setUsername(user.getUsername());
            userDetailDTO.setRole(user.getRole());
            userDetailDTO.setStatus(user.getStatus());
            userDetailDTO.setBranch(user.getBranch().toCommonDTO());
            userDetailDTO.setDepartment(user.getDepartment().toCommonDTO());
            userDetailDTO.setPosition(user.getPosition().toCommonDTO());
            userDetailDTO.setCompany(user.getCompany().toCommonDTO());
            return userDetailDTO;

        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    }


    public Page<UserListDTO> getList(UserFilter filter) {
        ResultList<User> resultList = userRepository.getResultList(filter);
        List<UserListDTO> result = resultList
                .getList()
                .stream()
                .map(user -> {
                    UserListDTO userListDTO = new UserListDTO();
                    userListDTO.setId(user.getId());
                    userListDTO.setFirstName(user.getFirstName());
                    userListDTO.setLastName(user.getLastName());
                    userListDTO.setPatronymic(user.getPatronymic());
                    userListDTO.setUsername(user.getUsername());
                    userListDTO.setStatus(user.getStatus());
                    userListDTO.setRole(user.getRole());
                    userListDTO.setBranch(user.getBranch().toCommonDTO());
                    return userListDTO;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }

    @Transactional
    public void updateStatus(Long id, UserStatus status) {
        userRepository
                .findById(id)
                .map(user -> {
                    user.setStatus(status);
                    userRepository.save(user);
                    return user;
                })
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

}


