package uz.behzod.restaurantApp.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.enums.UserStatus;
import uz.behzod.restaurantApp.repository.UserRepository;
import uz.behzod.restaurantApp.service.BaseService;


import java.util.List;


@Component()
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DomainUserDetailsService extends BaseService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository
                .findByUsernameAndDeletedIsFalse(username)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> badRequestExceptionThrow(ENTITY_NOT_FOUND_WITH, USER, NAME, username).get());
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
        if (UserStatus.PENDING == user.getStatus()) {
            throw badRequestExceptionThrow(NOT_ACTIVATED, USER).get();
        }
        if (UserStatus.IN_ACTIVE == user.getStatus()) {
            throw badRequestExceptionThrow(NOT_ACTIVATED, USER).get();
        }

        return new CustomUser(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().getCode())),
                user.getId(),
                user.getCompanyId(),
                user.getBranchId(),
                user.getDepartmentId(),
                user.getCompany() != null ? user.getCompany().getTin() : null
        );
    }
}
