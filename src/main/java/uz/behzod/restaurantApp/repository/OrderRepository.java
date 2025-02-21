package uz.behzod.restaurantApp.repository;


import uz.behzod.restaurantApp.domain.order.Order;
import uz.behzod.restaurantApp.repository.custom.OrderRepositoryCustom;

public interface OrderRepository extends BaseRepository<Order, Long>, OrderRepositoryCustom {

}
