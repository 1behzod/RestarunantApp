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
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.user.UserDTO;
import uz.behzod.restaurantApp.dto.user.admin.UserDetailAdminDTO;
import uz.behzod.restaurantApp.dto.user.admin.UserListAdminDTO;
import uz.behzod.restaurantApp.dto.user.cabinet.UserDetailCabinetDTO;
import uz.behzod.restaurantApp.dto.user.cabinet.UserListCabinetDTO;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.filters.user.UserFilter;
import uz.behzod.restaurantApp.repository.BranchRepository;
import uz.behzod.restaurantApp.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    UserRepository userRepository;
    BranchRepository branchRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public Long create(UserDTO userDTO) {
        User user = null;
        if (StringUtils.hasLength(userDTO.getUsername())) {
            user = userRepository.findFirstByUsernameAndDeletedIsFalse(userDTO.getUsername()).orElse(null);
        }
        if (user == null) {
            user = new User();
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPatronymic(userDTO.getPatronymic());
        user.setPositionId(userDTO.getPositionId());
        if (!StringUtils.hasLength(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username is required");
        }
        user.setUsername(userDTO.getUsername());
        String password = null;
        if (userDTO.getId() == null) {
            password = Optional.ofNullable(userDTO.getPassword()).orElse("");
            user.setPassword(passwordEncoder.encode(password));
        }
        user.setBranchId(userDTO.getBranchId());
//        if (userDTO.getBranchId() != null) {
//            Branch branch = branchRepository.findById(userDTO.getBranchId())
//                    .orElseThrow(() -> new IllegalArgumentException("Branch with ID " + userDTO.getBranchId() + " not found"));
//        }
        user.setRole(Role.valueOf(userDTO.getRole()));
        user.setBranchId(userDTO.getBranchId());
        user.setCompanyId(userDTO.getCompanyId());
        return userRepository.save(user).getId();
    }

    @Transactional
    public Long update(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            if (StringUtils.hasLength(userDTO.getUsername()) &&
                    !userDTO.getUsername().equals(user.getUsername()) &&
                    userRepository.existsByUsernameAndDeletedIsFalseAndIdNot(user.getId(), userDTO.getUsername())) {
                throw new IllegalArgumentException("Username already exists.");
            }

            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPatronymic(userDTO.getPatronymic());
            user.setUsername(userDTO.getUsername());

            if (userDTO.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            if (StringUtils.hasLength(userDTO.getRole()) &&
                    !user.getRole().name().equals(userDTO.getRole())) {
                user.setRole(Role.valueOf(userDTO.getRole()));
            }

            user.setBranchId(userDTO.getBranchId());
            user.setCompanyId(userDTO.getCompanyId());
            return userRepository.save(user).getId();
        }).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }


    public UserDetailCabinetDTO getForCabinet(Long id) {
        return userRepository.findById(id).map(user -> {
            UserDetailCabinetDTO userDetailDTO = new UserDetailCabinetDTO();
            userDetailDTO.setId(id);
            userDetailDTO.setFirstName(user.getFirstName());
            userDetailDTO.setLastName(user.getLastName());
            userDetailDTO.setPatronymic(user.getPatronymic());
            userDetailDTO.setUsername(user.getUsername());
            userDetailDTO.setStatus(user.getStatus());
            userDetailDTO.setBranch(user.getBranch().toCommonDTO());
            userDetailDTO.setRole(user.getRole());
            return userDetailDTO;
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));

    }


    public UserDetailAdminDTO getForAdmin(Long id) {
        return userRepository.findById(id).map(user -> {
            UserDetailAdminDTO userDetailDTO = new UserDetailAdminDTO();
            userDetailDTO.setId(id);
            userDetailDTO.setFirstName(user.getFirstName());
            userDetailDTO.setLastName(user.getLastName());
            userDetailDTO.setPatronymic(user.getPatronymic());
            userDetailDTO.setUsername(user.getUsername());
            userDetailDTO.setStatus(user.getStatus());
            userDetailDTO.setRole(user.getRole());
            if (user.getBranchId() != null) {
                userDetailDTO.setBranch(user.getBranch().toCommonDTO());
            }
            userDetailDTO.setCompany(user.getCompany().toCommonDTO());
            return userDetailDTO;
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }


    public Page<UserListCabinetDTO> getListForCabinet(UserFilter filter) {
        ResultList<User> resultList = userRepository.getResultList(filter);
        List<UserListCabinetDTO> result = resultList
                .getList()
                .stream()
                .map(user -> {
                    UserListCabinetDTO userListDTO = new UserListCabinetDTO();
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
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }


}


