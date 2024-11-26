package uz.behzod.restaurantApp.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.user.UserDTO;
import uz.behzod.restaurantApp.dto.user.admin.UserDetailAdminDTO;
import uz.behzod.restaurantApp.dto.user.cabinet.UserDetailCabinetDTO;
import uz.behzod.restaurantApp.dto.user.cabinet.UserListCabinetDTO;
import uz.behzod.restaurantApp.filters.user.UserFilter;
import uz.behzod.restaurantApp.service.UserService;

@RestController
@RequestMapping("/user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
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
    public ResponseEntity<UserDetailCabinetDTO> getForCabinet(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getForCabinet(id));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDetailAdminDTO> getForAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getForAdmin(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserListCabinetDTO>> getListForCabinet(UserFilter filter) {
        return ResponseEntity.ok(userService.getListForCabinet(filter));
    }
}
