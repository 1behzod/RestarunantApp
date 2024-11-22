package uz.behzod.restaurantApp.dto.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDetailDTO {

    CommonDTO region;

    CommonDTO district;

    CommonDTO neighbourhood;

    String street;
}
