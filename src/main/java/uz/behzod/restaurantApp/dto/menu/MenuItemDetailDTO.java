package uz.behzod.restaurantApp.dto.menu;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonCodeDTO;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuItemDetailDTO extends CommonDTO {

    CommonDTO product;

    BigDecimal price;

    CommonDTO menu;

    CommonCodeDTO unit;

}
