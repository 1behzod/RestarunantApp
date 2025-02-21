package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.branch.BranchDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchListDTO;
import uz.behzod.restaurantApp.dto.menu.MenuItemSalesSummary;
import uz.behzod.restaurantApp.dto.order.OrderDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.BranchService;
import uz.behzod.restaurantApp.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.create(orderDTO));
    }

    @GetMapping("/menu-item-sales/today")
    public ResponseEntity<ResultList<MenuItemSalesSummary>> getMenuItemSalesForToday() {
        return ResponseEntity.ok(orderService.getMenuItemSalesForToday());
    }

    @GetMapping("/menu-item-sales/week")
    public ResponseEntity<ResultList<MenuItemSalesSummary>> getMenuItemSalesForThisWeek() {
        return ResponseEntity.ok(orderService.getMenuItemSalesForThisWeek());
    }

    @GetMapping("/menu-item-sales/month")
    public ResponseEntity<ResultList<MenuItemSalesSummary>> getMenuItemSalesForThisMonth() {
        return ResponseEntity.ok(orderService.getMenuItemSalesForThisMonth());
    }

    @GetMapping("/menu-item-sales/day")
    public ResponseEntity<ResultList<MenuItemSalesSummary>> getMenuItemSalesForSpecificDay(@RequestParam("date") LocalDateTime date) {
        return ResponseEntity.ok(orderService.getMenuItemSalesForSpecificDay(date));
    }

    @GetMapping("/menu-item-sales/range")
    public ResponseEntity<ResultList<MenuItemSalesSummary>> getMenuItemSalesFromDateRange(@RequestParam("start") LocalDateTime startDate,
                                                                                          @RequestParam("end") LocalDateTime endDate) {
        return ResponseEntity.ok(orderService.getMenuItemSalesForDateRange(startDate, endDate));
    }

}
