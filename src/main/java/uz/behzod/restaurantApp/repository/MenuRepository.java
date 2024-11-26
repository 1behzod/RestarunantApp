package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.repository.custom.MenuRepositoryCustom;

public interface MenuRepository extends BaseRepository<Menu, Long>, MenuRepositoryCustom {

    boolean existsByNameAndBranchIdAndDeletedIsFalse(String name, Long branchId);
    boolean existsByNameAndBranchIdAndDeletedIsFalseAndIdNot(String name, Long branchId, Long id);

}
