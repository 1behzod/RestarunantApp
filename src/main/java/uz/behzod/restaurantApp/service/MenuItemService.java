package uz.behzod.restaurantApp.service;

import jakarta.servlet.http.PushBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.dto.menu.MenuItemDTO;
import uz.behzod.restaurantApp.dto.menu.MenuItemDetailDTO;
import uz.behzod.restaurantApp.repository.MenuItemRepository;
import uz.behzod.restaurantApp.repository.MenuRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuItemService {

    MenuItemRepository menuItemRepository;

    private void validate(MenuItemDTO menuItemDTO) {
        if (!StringUtils.hasLength(menuItemDTO.getName())) {
            throw new RuntimeException("Menu item name cannot be empty");
        }
        if (menuItemDTO.getMenuId() == null) {
            throw new RuntimeException("Menu item id cannot be empty");
        }
        if (menuItemDTO.getPrice() == null) {
            throw new RuntimeException("Menu item price cannot be empty");
        }
        if (menuItemDTO.getProductId() == null) {
            throw new RuntimeException("Menu item product id cannot be empty");
        }
        if (menuItemDTO.getUnitId() == null) {
            throw new RuntimeException("Menu item unitId cannot be empty");
        }


    }

    @Transactional
    public Long create(MenuItemDTO menuItemDTO) {
        validate(menuItemDTO);
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDTO.getName());
        menuItem.setMenuId(menuItemDTO.getMenuId());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setProductId(menuItemDTO.getProductId());
        menuItem.setUnitId(menuItemDTO.getUnitId());
        return menuItemRepository.save(menuItem).getId();
    }

    @Transactional
    public Long update(MenuItemDTO menuItemDTO, Long id) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu item not found"));
        menuItemDTO.setId(id);
        this.validate(menuItemDTO);

        menuItem.setName(menuItemDTO.getName());
        menuItem.setMenuId(menuItemDTO.getMenuId());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setProductId(menuItemDTO.getProductId());
        menuItem.setUnitId(menuItemDTO.getUnitId());
        return menuItemRepository.save(menuItem).getId();
    }

    @Transactional
    public Long delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemRepository.deleteById(id);
        return id;
    }

    public MenuItemDetailDTO get(Long id) {
        return menuItemRepository.findById(id).map(menuItem -> {
            MenuItemDetailDTO menuItemDetailDTO = new MenuItemDetailDTO();
            menuItemDetailDTO.setName(menuItem.getName());
            menuItemDetailDTO.setPrice(menuItem.getPrice());
            menuItemDetailDTO.setProductId(menuItem.getProductId());
            menuItemDetailDTO.setUnitId(menuItem.getUnitId());
            return menuItemDetailDTO;

        }).orElseThrow(() -> new RuntimeException("Menu item not found"));
    }
}