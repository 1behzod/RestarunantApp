package uz.behzod.restaurantApp.repository;

import uz.behzod.restaurantApp.domain.product.Product;
import uz.behzod.restaurantApp.repository.custom.ProductRepositoryCustom;

public interface ProductRepository extends BaseRepository<Product, Long>, ProductRepositoryCustom {

}
