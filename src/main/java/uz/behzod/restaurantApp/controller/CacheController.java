package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.behzod.restaurantApp.service.CacheService;

@RestController
@RequestMapping("/v1/caches")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CacheController {

    CacheService cacheService;


}
