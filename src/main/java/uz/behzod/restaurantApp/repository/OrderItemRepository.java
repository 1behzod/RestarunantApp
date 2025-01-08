package uz.behzod.restaurantApp.repository;


import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.domain.order.OrderItem;
import uz.behzod.restaurantApp.repository.custom.BranchRepositoryCustom;
import uz.behzod.restaurantApp.repository.custom.OrderItemRepositoryCustom;

public interface OrderItemRepository extends BaseRepository<OrderItem, Long>, OrderItemRepositoryCustom {


}
