package uz.behzod.restaurantApp.dto.branch;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import uz.behzod.restaurantApp.dto.address.AddressDTO;
import uz.behzod.restaurantApp.dto.base.CommonDTO;


@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BranchDTO extends CommonDTO {


    Long companyId;

    AddressDTO address;

}
