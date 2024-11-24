package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.repository.custom.MenuItemRepositoryCustom;

public interface MenuItemRepository extends BaseRepository<MenuItem, Long>, MenuItemRepositoryCustom {

}
