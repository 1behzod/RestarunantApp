package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Repository
public interface UserRepositoryCustom {

    ResultList<User> getResultList(BaseFilter filter);
}
