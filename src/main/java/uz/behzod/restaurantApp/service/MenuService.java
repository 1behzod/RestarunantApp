package uz.behzod.restaurantApp.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.dto.menu.MenuDTO;
import uz.behzod.restaurantApp.dto.menu.MenuDetailDTO;
import uz.behzod.restaurantApp.repository.MenuRepository;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    MenuRepository menuRepository;

    private void validate(MenuDTO menuDTO) {
        if (!StringUtils.hasLength(menuDTO.getName())) {
            throw new RuntimeException("Menu name cannot be empty");
        }
        if (menuDTO.getBranchId() == null) {
            throw new RuntimeException("Menu branch id cannot be empty");
        }
        if (menuRepository.existsByName(menuDTO.getName())) {
            throw new RuntimeException("Menu name already exists");
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
    public Long update(MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuDTO.getId()).orElseThrow(() -> new RuntimeException("Menu not found"));
        menu.setId(menuDTO.getId());
        this.validate(menuDTO);

        menu.setName(menuDTO.getName());
        menu.setBranchId(menuDTO.getBranchId());
        return menuRepository.save(menu).getId();

    }

    @Transactional
    public Long delete(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new RuntimeException("Menu not found");
        }
        menuRepository.deleteById(id);
        return id;
    }


    public MenuDetailDTO get(Long id) {
        return  menuRepository.findById(id).map(menu -> {

            MenuDetailDTO menuDetailDTO = new MenuDetailDTO();
            menuDetailDTO.setName(menu.getName());
            menuDetailDTO.setBranchId(menu.getBranchId());
            return menuDetailDTO;
        }).orElseThrow(() -> new RuntimeException("Menu not found"));
    }




}
