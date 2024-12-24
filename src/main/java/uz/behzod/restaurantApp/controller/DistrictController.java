package uz.behzod.restaurantApp.controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.address.District;
import uz.behzod.restaurantApp.service.DistrictService;

import java.util.List;

@RestController
@RequestMapping("/v1/districts")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DistrictController {

    DistrictService districtService;

    @Cacheable(cacheNames = CacheConstants.DISTRICT_BY_DISTRICT_ID)
    @GetMapping
    public ResponseEntity<List<District>> getList() {
        return ResponseEntity.ok(districtService.getList());
    }

    @Cacheable(cacheNames = CacheConstants.DISTRICT_BY_REGION_ID)
    @GetMapping("/{id}")
    public ResponseEntity<List<District>> getListByRegionId(@PathVariable Long id) {
        return ResponseEntity.ok(districtService.getListByRegionId(id));
    }


}
