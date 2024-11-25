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
import uz.behzod.restaurantApp.filters.menu.MenuFilter;
import uz.behzod.restaurantApp.repository.MenuRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MenuService {

    MenuRepository menuRepository;

    private void validate(MenuDTO menuDTO) {
        if (!StringUtils.hasLength(menuDTO.getName())) {
            throw new RuntimeException("Menu name cannot be empty");
        }
        if (menuDTO.getBranchId() == null) {
            throw new RuntimeException("Menu branch id cannot be empty");
        }
        if (menuDTO.getId() == null) {
            if (menuRepository.existsByName(menuDTO.getName())) {
                throw new RuntimeException("Menu name already exists");
            }
        }
        if (menuDTO.getId() != null) {
            if (menuRepository.existsByName(menuDTO.getName())) {
                throw new RuntimeException("Menu name already exists");
            }
        }
        //TODO current branch bilan tekshirish kere, har hil branchda bir hil menu yaratib bolmadi
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
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setId(id);
        this.validate(menuDTO);

        menu.setName(menuDTO.getName());
        menu.setBranchId(menuDTO.getBranchId());
        return menuRepository.save(menu).getId();

    }

    @Transactional
    public void delete(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu not found");
        }
        menuRepository.deleteById(id);
    }


    public MenuDetailDTO get(Long id) {
        return menuRepository.findById(id).map(menu -> {
            MenuDetailDTO menuDetailDTO = new MenuDetailDTO();
            menuDetailDTO.setId(menu.getId());
            menuDetailDTO.setName(menu.getName());
            menuDetailDTO.setBranchId(menu.getBranchId());
            return menuDetailDTO;
        }).orElseThrow(() -> new RuntimeException("Menu not found"));
    }


    public Page<MenuListDTO> getList(MenuFilter filter) {
        ResultList<Menu> resultList = menuRepository.getResultList(filter);
        List<MenuListDTO> result = resultList
                .getList()
                .stream()
                .map(menu -> {
                    MenuListDTO menuListDTO = new MenuListDTO();
                    menuListDTO.setId(menu.getId());
                    menuListDTO.setName(menu.getName());
                    menuListDTO.setBranchId(menu.getBranchId());
                    return menuListDTO;
                }).collect(Collectors.toList());
        return new PageImpl<>(result, filter.getOrderedPageable(), resultList.getCount());
    }

}
