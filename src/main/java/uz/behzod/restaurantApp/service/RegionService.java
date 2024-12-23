package uz.behzod.restaurantApp.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import uz.behzod.restaurantApp.domain.address.Region;
import uz.behzod.restaurantApp.repository.RegionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegionService {

    RegionRepository regionRepository;

    public List<Region> getList() {
        return regionRepository.findAll();
    }
}
