package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.repository.custom.MenuRepositoryCustom;

public interface MenuRepository extends BaseRepository<Menu, Long>, MenuRepositoryCustom {

    boolean existsByName(String name);


}
