package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.token.TokenDTO;
import uz.behzod.restaurantApp.dto.user.UserDTO;
import uz.behzod.restaurantApp.dto.user.UserDetailDTO;
import uz.behzod.restaurantApp.dto.user.UserListDTO;
import uz.behzod.restaurantApp.dto.user.UserLoginDTO;
import uz.behzod.restaurantApp.enums.UserStatus;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.UserRepository;
import uz.behzod.restaurantApp.security.DomainUserDetailsService;
import uz.behzod.restaurantApp.security.jwt.TokenProvider;

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
    AuthenticationManager authenticationManager;
    TokenProvider tokenProvider;
    UserDetailsService userDetailsService;
    DomainUserDetailsService domainUserDetailsService;

    private void validate(UserDTO userDTO) {
        if (!StringUtils.hasLength(userDTO.getFirstName())) {
            throw badRequestExceptionThrow(REQUIRED, NAME).get();
        }
        if (!StringUtils.hasLength(userDTO.getUsername())) {
            throw badRequestExceptionThrow(REQUIRED, USERNAME).get();
        }
        if (!StringUtils.hasLength(userDTO.getPassword())) {
            throw badRequestExceptionThrow(REQUIRED, PASSWORD).get();
        }
        if (userDTO.getBranchId() == null) {
            throw badRequestExceptionThrow(REQUIRED, BRANCH).get();
        }
        if (userDTO.getRole() == null) {
            throw badRequestExceptionThrow(REQUIRED, ROLE).get();
        }
        if (userDTO.getDepartmentId() == null) {
            throw badRequestExceptionThrow(REQUIRED, DEPARTMENT).get();
        }
        if (userDTO.getPositionId() == null) {
            throw badRequestExceptionThrow(REQUIRED, POSITION).get();
        }
        if (userDTO.getCompanyId() == null) {
            throw badRequestExceptionThrow(REQUIRED, COMPANY).get();
        }

// AccountService  UserSessionService
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
        userRepository.save(user);
        cacheService.evict(CacheConstants.USER_BY_LOGIN, user.getUsername());
        return user.getId();
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

    public TokenDTO login(UserLoginDTO userLoginDTO, boolean rememberMe) {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(userLoginDTO.getUsername());

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String jwt = tokenProvider.createToken(userDetails.getUsername(), rememberMe);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccess_token(jwt);
        tokenDTO.setToken_type("Bearer");
        tokenDTO.setExpire(tokenProvider.getTokenExpiryInSeconds(rememberMe));
        return tokenDTO;
    }
}



