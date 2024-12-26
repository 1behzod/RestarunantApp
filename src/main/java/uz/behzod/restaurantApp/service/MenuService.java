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
import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.menu.MenuDTO;
import uz.behzod.restaurantApp.dto.menu.MenuDetailDTO;
import uz.behzod.restaurantApp.dto.menu.MenuListDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.MenuRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class MenuService extends BaseService {

    MenuRepository menuRepository;

    private void validate(MenuDTO menuDTO) {
        if (!StringUtils.hasLength(menuDTO.getName())) {
            throw badRequestExceptionThrow(REQUIRED, NAME).get();
        }
        if (menuDTO.getBranchId() == null) {
            throw badRequestExceptionThrow(REQUIRED, BRANCH).get();
        }
        if (menuDTO.getId() == null) {
            if (menuRepository.existsByNameAndBranchIdAndDeletedIsFalse(menuDTO.getName(), menuDTO.getBranchId())) {
                throw conflictExceptionThrow(ENTITY_ALREADY_EXISTS_WITH, MENU, NAME, menuDTO.getName()).get();
            }
        }
        if (menuDTO.getId() != null) {
            if (menuRepository.existsByNameAndBranchIdAndDeletedIsFalseAndIdNot(menuDTO.getName(), menuDTO.getBranchId(), menuDTO.getId())) {
                throw conflictExceptionThrow(ENTITY_ALREADY_EXISTS_WITH, MENU, NAME, menuDTO.getName()).get();
            }
        }
    }

    @Transactional
    public Long create(MenuDTO menuDTO) {
        this.validate(menuDTO);
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setBranchId(menuDTO.getBranchId());
        return menuRepository.save(menu).getId();
    }

    @Transactional
    public Long update(Long id, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(id).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU));
        menu.setId(id);
        this.validate(menuDTO);

        menu.setName(menuDTO.getName());
        menu.setBranchId(menuDTO.getBranchId());
        return menuRepository.save(menu).getId();

    }

    @Transactional
    public void delete(Long id) {
        if (!menuRepository.existsById(id)) {
            throw notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU).get();
        }
        menuRepository.deleteById(id);
    }


    public MenuDetailDTO get(Long id) {
        return menuRepository.findById(id).map(menu -> {
            MenuDetailDTO menuDetailDTO = new MenuDetailDTO();
            menuDetailDTO.setId(menu.getId());
            menuDetailDTO.setName(menu.getName());
            menuDetailDTO.setBranch(menu.getBranch().toCommonDTO());
            return menuDetailDTO;
        }).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU));
    }


    public Page<MenuListDTO> getList(BaseFilter filter) {
        ResultList<Menu> resultList = menuRepository.getResultList(filter);
        List<MenuListDTO> result = resultList
                .getList()
                .stream()
                .map(menu -> {
                    MenuListDTO menuListDTO = new MenuListDTO();
                    menuListDTO.setId(menu.getId());
                    menuListDTO.setName(menu.getName());
                    menuListDTO.setBranch(menu.getBranch().toCommonDTO());
                    return menuListDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }

}
