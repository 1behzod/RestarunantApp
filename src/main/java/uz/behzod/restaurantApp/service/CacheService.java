package uz.behzod.restaurantApp.service;


import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CacheService {

    CacheManager cacheManager;


    public List<String> getList() {
        return new ArrayList<>((Collection<String>) cacheManager.getCacheNames());
    }

    public void clear(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    public void clearAll() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            try {
                this.clear(cacheName);
            } catch (Exception e) {
                log.error("Clear cache {} error {} :", cacheName, e.getMessage());
            }
        });
    }

    public void evict(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }
}
