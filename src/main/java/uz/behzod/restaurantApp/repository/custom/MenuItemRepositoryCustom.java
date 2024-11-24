package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.menu.MenuItemFilter;

@Repository
public interface MenuItemRepositoryCustom {
    ResultList<MenuItem> getResultList(MenuItemFilter filter);
}
