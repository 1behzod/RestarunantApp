package uz.behzod.restaurantApp.service;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.behzod.restaurantApp.domain.address.District;
import uz.behzod.restaurantApp.repository.DistrictRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DistrictService {

    DistrictRepository districtRepository;

    public List<District> getList() {
        return districtRepository.findAll();
    }

    public List<District> getListByRegionId(Long id) {
        return districtRepository.getListByRegionId(id);
    }
}
