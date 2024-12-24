package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.address.Region;
import uz.behzod.restaurantApp.service.RegionService;

import java.util.List;

@RestController
@RequestMapping("/v1/regions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegionController {

    RegionService regionService;

    @Cacheable(cacheNames = CacheConstants.REGION_BY_REGION_ID)
    @GetMapping
    public ResponseEntity<List<Region>> getList() {
        return ResponseEntity.ok(regionService.getList());

    }
}
