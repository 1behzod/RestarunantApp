package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import uz.behzod.restaurantApp.domain.address.Address;
import uz.behzod.restaurantApp.domain.branch.Branch;
import uz.behzod.restaurantApp.domain.menu.MenuItem;
import uz.behzod.restaurantApp.domain.order.Order;
import uz.behzod.restaurantApp.domain.order.OrderItem;
import uz.behzod.restaurantApp.dto.address.AddressDetailDTO;
import uz.behzod.restaurantApp.dto.base.ResultList;
import uz.behzod.restaurantApp.dto.branch.BranchDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchListDTO;
import uz.behzod.restaurantApp.dto.order.OrderDTO;
import uz.behzod.restaurantApp.dto.order.OrderItemDTO;
import uz.behzod.restaurantApp.enums.OrderStatus;
import uz.behzod.restaurantApp.enums.PaymentStatus;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.repository.BranchRepository;
import uz.behzod.restaurantApp.repository.MenuItemRepository;
import uz.behzod.restaurantApp.repository.OrderItemRepository;
import uz.behzod.restaurantApp.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional(readOnly = true)
public class OrderService extends BaseService {

    OrderRepository orderRepository;
    MenuItemRepository menuItemRepository;
    OrderItemRepository orderItemRepository;

    private void validate(OrderDTO orderDTO) {
    }

    private void validateItem(OrderItemDTO orderItemDTO) {
        if (orderItemDTO.getMenuItemId() == null) {
            throw badRequestExceptionThrow(REQUIRED, MENU_ITEM).get();
        }
        if (orderItemDTO.getUnitId() == null) {
            throw badRequestExceptionThrow(REQUIRED, UNIT).get();
        }
        if (orderItemDTO.getPrice() == null || orderItemDTO.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            throw badRequestExceptionThrow(REQUIRED, PRICE).get();
        }
        if (orderItemDTO.getQty() == null || orderItemDTO.getQty().compareTo(BigDecimal.ZERO) == 0) {
            throw badRequestExceptionThrow(REQUIRED, QTY).get();
        }
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
}
