package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.product.Product;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.filters.BaseFilter;

@Repository
public interface ProductRepositoryCustom {

    ResultList<Product> getResultList(BaseFilter filter);
}
