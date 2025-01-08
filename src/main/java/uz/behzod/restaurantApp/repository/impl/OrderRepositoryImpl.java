package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.menu.MenuItemSalesSummary;
import uz.behzod.restaurantApp.repository.custom.OrderRepositoryCustom;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;


public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ResultList<MenuItemSalesSummary> getMenuItemSalesForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        ResultList<MenuItemSalesSummary> result = new ResultList<>();
        String sql = "SELECT " +
                "menu_item_id AS menuItemId, " +
                "COUNT(*) AS totalOrders, " +
                "SUM(qty) AS totalQtySold, " +
                "SUM(qty * price) AS totalSales " +
                "FROM order_item " +
                "WHERE order_date BETWEEN :startDate AND :endDate " +
                "GROUP BY menu_item_id " +
                "ORDER BY totalSales DESC";

        Query query = entityManager.createNativeQuery(sql, "MenuItemSalesSummaryMapping");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<MenuItemSalesSummary> summaries = query.getResultList();

        result.setList(summaries);

        String countSql = "SELECT COUNT(DISTINCT menu_item_id) " +
                "FROM order_item " +
                "WHERE order_date BETWEEN :startDate AND :endDate";

        Query countQuery = entityManager.createNativeQuery(countSql);
        countQuery.setParameter("startDate", startDate);
        countQuery.setParameter("endDate", endDate);

        Long totalCount = ((Number) countQuery.getSingleResult()).longValue();
        result.setCount(totalCount);

        return result;

    }




    @Override
    public ResultList<MenuItemSalesSummary> getMenuItemSalesForToday() {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay().plusMinutes(1);
        LocalDateTime now = LocalDateTime.now();
        return getMenuItemSalesForDateRange(startOfDay, now);
    }

    @Override
    public ResultList<MenuItemSalesSummary> getMenuItemSalesForThisWeek() {
        LocalDateTime startOfWeek = LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
                .toLocalDate()
                .atStartOfDay()
                .plusMinutes(1);
        LocalDateTime now = LocalDateTime.now();
        return getMenuItemSalesForDateRange(startOfWeek, now);
    }


    @Override
    public ResultList<MenuItemSalesSummary> getMenuItemSalesForThisMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = LocalDateTime.now();
        return getMenuItemSalesForDateRange(startOfMonth, endOfMonth);
    }

    @Override
    public ResultList<MenuItemSalesSummary> getMenuItemSalesForSpecificDay(LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);
        return getMenuItemSalesForDateRange(startOfDay, endOfDay);
    }
}
