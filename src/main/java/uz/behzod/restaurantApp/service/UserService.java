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
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.user.UserDTO;
import uz.behzod.restaurantApp.dto.user.UserDetailDTO;
import uz.behzod.restaurantApp.dto.user.UserListDTO;
import uz.behzod.restaurantApp.enums.UserStatus;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.UserRepository;
import uz.behzod.restaurantApp.validator.ValidationContext;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService extends BaseService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    CacheService cacheService;
    ValidationContext validationContext;

    @Transactional
    public Long create(UserDTO userDTO) {
        validationContext.validate(userDTO);
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
        userRepository.save(user);
        cacheService.evict(CacheConstants.USER_BY_LOGIN, user.getUsername());
        return user.getId();
    }

    @Transactional
    public Long update(Long id, UserDTO userDTO) {
        userDTO.setId(id);
        validationContext.validate(userDTO);
        return userRepository.findById(id).map(user -> {
            user.setLastName(userDTO.getLastName());
            user.setPatronymic(userDTO.getPatronymic());
            user.setUsername(userDTO.getUsername());
            user.setRole(userDTO.getRole());
            user.setPositionId(userDTO.getPositionId());
            user.setDepartmentId(userDTO.getDepartmentId());
            user.setBranchId(userDTO.getBranchId());
            userRepository.save(user);
            cacheService.evict(CacheConstants.USER_BY_LOGIN, user.getUsername());
            return user.getId();
        }).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, USER));
    }


    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .map(user -> {
                    user.setStatus(UserStatus.IN_ACTIVE);
                    userRepository.deleteById(id);
                    cacheService.evict(CacheConstants.USER_BY_LOGIN, user.getUsername());
                    return user;
                }).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, USER));
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

        }).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, USER));

    }


    public Page<UserListDTO> getList(BaseFilter filter) {
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
                    cacheService.evict(CacheConstants.USER_BY_LOGIN, user.getUsername());
                    return user.getId();
                })
                .orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, USER));
    }

}


