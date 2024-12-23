package uz.behzod.restaurantApp.repository;

import org.springframework.cache.annotation.Cacheable;
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.address.Region;

import java.util.List;

public interface RegionRepository extends BaseRepository<Region, Long> {

    @Cacheable(cacheNames = CacheConstants.REGION_BY_REGION_ID)
    List<Region> findAll();
}
