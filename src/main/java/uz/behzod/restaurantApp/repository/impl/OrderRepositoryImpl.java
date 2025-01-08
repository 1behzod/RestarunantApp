package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.menu.MenuItemSalesSummary;
import uz.behzod.restaurantApp.repository.custom.OrderRepositoryCustom;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public ResultList<MenuItemSalesSummary> getMenuItemSalesForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        ResultList<MenuItemSalesSummary> result = new ResultList<>();
        return null;
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
