package uz.behzod.restaurantApp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.dto.user.UserDTO;
import uz.behzod.restaurantApp.enums.Role;
import uz.behzod.restaurantApp.repository.UserRepository;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Transactional
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }


}


