package uz.behzod.restaurantApp.dto.company;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.address.AddressDetailDTO;
import uz.behzod.restaurantApp.dto.base.CommonDTO;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDetailDTO extends CommonDTO {

    String brand;

    String tin;

    String pinfl;

    AddressDetailDTO address;

}
