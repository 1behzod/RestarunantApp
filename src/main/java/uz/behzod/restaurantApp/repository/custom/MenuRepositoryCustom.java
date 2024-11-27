package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Repository
public interface MenuRepositoryCustom {

    ResultList<Menu> getResultList(BaseFilter filter);
}
