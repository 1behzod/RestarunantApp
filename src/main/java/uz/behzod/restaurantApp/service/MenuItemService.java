package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.menu.MenuItemDTO;
import uz.behzod.restaurantApp.dto.menu.MenuItemDetailDTO;
import uz.behzod.restaurantApp.dto.menu.MenuItemListDTO;
import uz.behzod.restaurantApp.filters.menu.MenuItemFilter;
import uz.behzod.restaurantApp.repository.MenuItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuItemService {

    MenuItemRepository menuItemRepository;

    private void validate(MenuItemDTO menuItemDTO) {
        if (!StringUtils.hasLength(menuItemDTO.getName())) {
            throw new RuntimeException("Item name is required");
        }
        if (menuItemDTO.getMenuId() == null) {
            throw new RuntimeException("Menu id is required");
        }
        if (menuItemDTO.getPrice() == null) {
            throw new RuntimeException("Item price is required");
        }
        if (menuItemDTO.getProductId() == null) {
            throw new RuntimeException("Item product is required");
        }
        if (menuItemDTO.getUnitId() == null) {
            throw new RuntimeException("Item unit is required");
        }
        //TODO validation for product and unit

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
    public Long update(Long id, MenuItemDTO menuItemDTO) {
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
    public void delete(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new RuntimeException("Menu item not found");
        }
        menuItemRepository.deleteById(id);
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

    public Page<MenuItemListDTO> getList(MenuItemFilter filter) {
        ResultList<MenuItem> resultList = menuItemRepository.getResultList(filter);
        List<MenuItemListDTO> result = resultList
                .getList()
                .stream()
                .map(menuItem -> {
                    MenuItemListDTO menuItemListDTO = new MenuItemListDTO();
                    menuItemListDTO.setId(menuItem.getId());
                    menuItemListDTO.setName(menuItem.getName());
                    menuItemListDTO.setMenuId(menuItem.getMenuId());
                    return menuItemListDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }
}