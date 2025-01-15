package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.MenuItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class MenuItemService extends BaseService {

    MenuItemRepository menuItemRepository;

    private void validate(MenuItemDTO menuItemDTO) {
        nameValidator.validate(menuItemDTO.getName());
        menuValidator.validate(menuItemDTO.getMenuId());
        priceValidator.validate(menuItemDTO.getPrice());
        productValidator.validate(menuItemDTO.getProductId());
        unitValidator.validate(menuItemDTO.getUnitId());
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
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU_ITEM));
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
            throw notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU_ITEM).get();
        }
        menuItemRepository.deleteById(id);
    }

    public MenuItemDetailDTO get(Long id) {
        return menuItemRepository.findById(id).map(menuItem -> {
            MenuItemDetailDTO menuItemDetailDTO = new MenuItemDetailDTO();
            menuItemDetailDTO.setName(menuItem.getName());
            menuItemDetailDTO.setPrice(menuItem.getPrice());
            menuItemDetailDTO.setProduct(menuItem.getProduct().toCommonDTO());
            //  menuItemDetailDTO.setUnit(menuItem.getUnit().toCommonDTO());
            return menuItemDetailDTO;

        }).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU_ITEM));
    }

    public Page<MenuItemListDTO> getList(BaseFilter filter) {
        ResultList<MenuItem> resultList = menuItemRepository.getResultList(filter);
        List<MenuItemListDTO> result = resultList
                .getList()
                .stream()
                .map(menuItem -> {
                    MenuItemListDTO menuItemListDTO = new MenuItemListDTO();
                    menuItemListDTO.setId(menuItem.getId());
                    menuItemListDTO.setName(menuItem.getName());
                    menuItemListDTO.setMenu(menuItem.getMenu().toCommonDTO());
                    return menuItemListDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }
}