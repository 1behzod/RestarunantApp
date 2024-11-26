package uz.behzod.restaurantApp.dto.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemDTO extends CommonDTO {

    Long productId;

    BigDecimal price;

    Long menuId;

    Long unitId;
}
