package uz.behzod.restaurantApp.repository.custom;

import org.springframework.stereotype.Repository;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.domain.order.Order;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.menu.MenuItemSalesSummary;
import uz.behzod.restaurantApp.filters.BaseFilter;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepositoryCustom {

    ResultList<MenuItemSalesSummary> getMenuItemSalesForDateRange(LocalDateTime startDate, LocalDateTime endDate);

    ResultList<MenuItemSalesSummary> getMenuItemSalesForToday();

    ResultList<MenuItemSalesSummary> getMenuItemSalesForThisWeek();

    ResultList<MenuItemSalesSummary> getMenuItemSalesForThisMonth();

    ResultList<MenuItemSalesSummary> getMenuItemSalesForSpecificDay(LocalDateTime date);
}
