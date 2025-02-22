package uz.behzod.restaurantApp.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.token.TokenDTO;
import uz.behzod.restaurantApp.dto.user.*;
import uz.behzod.restaurantApp.enums.UserStatus;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.UserService;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@CrossOrigin(origins = "http://localhost:63342") // Allow frontend to send requests
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.create(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserListDTO>> getList(BaseFilter filter) {
        return ResponseEntity.ok(userService.getList(filter));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestParam UserStatus status) {
        userService.updateStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        boolean rememberMe = userLoginDTO.isRememberMe();
        TokenDTO tokenDTO = userService.login(userLoginDTO, rememberMe);
        return ResponseEntity.ok(tokenDTO);
    }

/*    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return ResponseEntity.ok().build();
    }*/
}
