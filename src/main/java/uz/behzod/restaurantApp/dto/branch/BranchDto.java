package uz.behzod.restaurantApp.dto.branch;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class BranchDto extends CommonDTO {


    Long companyId;

    AddressDTO address;

}
