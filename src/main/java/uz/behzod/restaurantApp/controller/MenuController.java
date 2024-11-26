package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.menu.MenuDTO;
import uz.behzod.restaurantApp.dto.menu.MenuDetailDTO;
import uz.behzod.restaurantApp.dto.menu.MenuListDTO;
import uz.behzod.restaurantApp.filters.menu.MenuFilter;
import uz.behzod.restaurantApp.service.MenuService;

@RestController
@RequestMapping("/api/menus")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MenuController {

    MenuService menuService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(menuService.create(menuDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        return ResponseEntity.ok(menuService.update(id, menuDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.get(id));
    }

    @GetMapping
    public ResponseEntity<Page<MenuListDTO>> getList(@ParameterObject MenuFilter filter) {
        return ResponseEntity.ok(menuService.getList(filter));
    }


}
