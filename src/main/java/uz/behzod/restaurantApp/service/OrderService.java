package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.domain.order.Order;
import uz.behzod.restaurantApp.domain.order.OrderItem;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.menu.MenuItemSalesSummary;
import uz.behzod.restaurantApp.dto.order.OrderDTO;
import uz.behzod.restaurantApp.dto.order.OrderItemDTO;
import uz.behzod.restaurantApp.enums.OrderStatus;
import uz.behzod.restaurantApp.enums.PaymentStatus;
import uz.behzod.restaurantApp.repository.MenuItemRepository;
import uz.behzod.restaurantApp.repository.OrderItemRepository;
import uz.behzod.restaurantApp.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class OrderService extends BaseService {


    private void validateItem(OrderItemDTO orderItemDTO) {
        menuItemValidator.validate(orderItemDTO.getUnitId());
        unitValidator.validate(orderItemDTO.getUnitId());
        priceValidator.validate(orderItemDTO.getPrice());
 //       qtyValidator.validate(orderItemDTO.getQty());
        if (orderItemDTO.getTotalPrice() == null || orderItemDTO.getTotalPrice().compareTo(BigDecimal.ZERO) == 0) {
            throw badRequestExceptionThrow(REQUIRED, PRICE).get();
        }

    }

    @Transactional
    public Long create(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUid(String.valueOf((UUID.randomUUID().toString())));
        order.setWaiterId(orderDTO.getWaiterId());
        order.setBranchId(orderDTO.getBranchId());
        order.setStatus(OrderStatus.valueOf(orderDTO.getOrderStatus()));
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setPaymentStatus(PaymentStatus.getByCode(orderDTO.getPaymentStatus()));
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        for (OrderItemDTO orderItemDTO : orderDTO.getItems()) {
            validateItem(orderItemDTO);
            MenuItem menuItem = menuItemRepository.findById(orderItemDTO.getMenuItemId()).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, MENU_ITEM));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setMenuItemId(menuItem.getId());
            orderItem.setUnitId(orderItemDTO.getUnitId());
            orderItem.setQty(orderItemDTO.getQty());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
            orderItemRepository.save(orderItem);
        }
        return order.getId();
    }

/*    @Transactional
    public Long update(OrderDTO orderDTO) {
        orderRepository.findById(orderDTO.getId()).orElseThrow(notFoundExceptionThrow(ENTITY_NOT_FOUND, ORDER));


    }*/


    public ResultList<MenuItemSalesSummary> getMenuItemSalesForDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getMenuItemSalesForDateRange(startDate, endDate);
    }

    public ResultList<MenuItemSalesSummary> getMenuItemSalesForToday() {
        return orderRepository.getMenuItemSalesForToday();
    }

    public ResultList<MenuItemSalesSummary> getMenuItemSalesForThisWeek() {
        return orderRepository.getMenuItemSalesForThisWeek();
    }

    public ResultList<MenuItemSalesSummary> getMenuItemSalesForThisMonth() {
        return orderRepository.getMenuItemSalesForThisMonth();
    }

    public ResultList<MenuItemSalesSummary> getMenuItemSalesForSpecificDay(LocalDateTime date) {
        return orderRepository.getMenuItemSalesForSpecificDay(date);
    }
}
