package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.menu.MenuItemDTO;
import uz.behzod.restaurantApp.dto.menu.MenuItemDetailDTO;
import uz.behzod.restaurantApp.dto.menu.MenuItemListDTO;
import uz.behzod.restaurantApp.filters.menu.MenuItemFilter;
import uz.behzod.restaurantApp.service.MenuItemService;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MenuItemController {

    MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody MenuItemDTO menuItemDTO) {
        return ResponseEntity.ok(menuItemService.create(menuItemDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@RequestBody Long id, MenuItemDTO menuItemDTO) {
        return ResponseEntity.ok(menuItemService.update(id, menuItemDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<MenuItemListDTO>> getList(MenuItemFilter filter) {
        return ResponseEntity.ok(menuItemService.getList(filter));
    }


}
