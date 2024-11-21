package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.menu.Menu;

public interface MenuRepository extends BaseRepository<Menu, Long> {

    boolean existsByName(String name);


}
