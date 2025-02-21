package uz.behzod.restaurantApp.dto.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO extends CommonDTO {

    Long waiterId;

    Long branchId;

    String OrderStatus;

    BigDecimal totalPrice;

    String paymentStatus;

    LocalDateTime orderDate;

    List<OrderItemDTO> items;
}
