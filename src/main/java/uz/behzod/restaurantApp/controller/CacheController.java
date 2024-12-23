package uz.behzod.restaurantApp.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.behzod.restaurantApp.service.CacheService;

import java.util.List;

@RestController
@RequestMapping("/v1/caches")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CacheController {

    CacheService cacheService;


    @GetMapping
    public ResponseEntity<List<String>> getList() {
        return ResponseEntity.ok(cacheService.getList());
    }

    @PutMapping("/clear")
    public ResponseEntity<Void> clear(@RequestBody String cacheName) {
        cacheService.clear(cacheName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear-all")
    public ResponseEntity<Void> clearAll() {
        cacheService.clearAll();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/evict")
    public ResponseEntity<Void> evict(@RequestBody String cacheName, @RequestParam String key) {
        cacheService.evict(cacheName, key);
        return ResponseEntity.ok().build();
    }

}
