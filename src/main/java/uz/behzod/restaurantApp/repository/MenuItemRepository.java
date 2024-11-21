package uz.behzod.restaurantApp.repository;

import jakarta.validation.constraints.NotNull;
import uz.behzod.restaurantApp.domain.menu.MenuItem;

public interface MenuItemRepository extends BaseRepository<MenuItem, Long> {


    boolean existsByName(@NotNull String name);
}
