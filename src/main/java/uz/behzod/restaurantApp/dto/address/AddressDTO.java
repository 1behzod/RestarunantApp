package uz.behzod.restaurantApp.dto.address;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AddressDTO {

    private Long regionId;

    private Long districtId;

    private Long neighbourhoodId;

    private String street;

}
