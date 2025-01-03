package uz.behzod.restaurantApp.dto.address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {

    Long regionId;

    Long districtId;

    Long neighbourhoodId;

    String street;

}
