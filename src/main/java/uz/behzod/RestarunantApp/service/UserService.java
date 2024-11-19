package uz.behzod.RestarunantApp.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.behzod.RestarunantApp.domain.auth.User;
import uz.behzod.RestarunantApp.repository.UserRepository;

import java.util.List;
import java.util.logging.Logger;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public User getUser(String username) {
        log.info("Fetching user by username: " + username);
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional
    public User createUser(User user) {
        log.info("Creating new user : {} ", user);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("Deleting user : {} ", userId);
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userRepository.deleteById(userId);
    }

    public User getUserById(Long userId) {
        log.info("Fetching user by ID : {} ", userId);
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

    }

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public boolean userExists(String username) {
        log.info("Checking if user exists with username: {}", username);
        return userRepository.existsByUsername(username);
    }


    public User authenticateUser(String username, String password) {
        log.info("Authenticating user with username: {}", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        return user;
    }

    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        log.info("Changing password for user with ID: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        user.setPassword(passwordEncoder.encode(oldPassword));
        userRepository.save(user);
    }

    public void updateUser(User updateUser) {
        log.info("Updating user : {}", updateUser);
        User user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(updateUser.getUsername());
        user.setPassword(updateUser.getPassword());
        userRepository.save(user);
    }

}
