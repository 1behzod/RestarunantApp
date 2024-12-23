package uz.behzod.restaurantApp.repository;

import org.springframework.cache.annotation.Cacheable;
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.address.District;

import java.util.List;

public interface DistrictRepository extends BaseRepository<District, Long> {

    @Cacheable(cacheNames = CacheConstants.DISTRICT_BY_DISTRICT_ID)
    List<District> findAll();

    @Cacheable(cacheNames = CacheConstants.DISTRICT_BY_REGION_ID)
    List<District> getListByRegionId(Long regionId);
}
