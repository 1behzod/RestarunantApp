package uz.behzod.restaurantApp.dto.order;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemDTO extends CommonDTO {

    Long menuItemId;

    Long unitId;

    BigDecimal qty;

    BigDecimal price;

    BigDecimal totalPrice;

    Long orderId;

    LocalDateTime orderDate;
}
