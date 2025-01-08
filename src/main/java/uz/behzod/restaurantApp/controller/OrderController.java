package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.dto.branch.BranchDTO;
import uz.behzod.restaurantApp.dto.branch.BranchDetailDTO;
import uz.behzod.restaurantApp.dto.branch.BranchListDTO;
import uz.behzod.restaurantApp.dto.order.OrderDTO;
import uz.behzod.restaurantApp.filters.BaseFilter;
import uz.behzod.restaurantApp.service.BranchService;
import uz.behzod.restaurantApp.service.OrderService;

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

}
