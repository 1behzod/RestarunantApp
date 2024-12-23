package uz.behzod.restaurantApp.config;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.behzod.restaurantApp.constants.CacheConstants;
import uz.behzod.restaurantApp.domain.address.District;
import uz.behzod.restaurantApp.domain.address.Neighbourhood;
import uz.behzod.restaurantApp.domain.address.Region;
import uz.behzod.restaurantApp.domain.auth.User;
import uz.behzod.restaurantApp.domain.company.Company;
import uz.behzod.restaurantApp.domain.menu.Menu;
import uz.behzod.restaurantApp.domain.unit.Unit;


@Configuration
@EnableCaching
public class CacheConfiguration implements CacheConstants {


    /*@Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }*/

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.javax.cache.cache_manager", cacheManager);
        };
    }


    @Bean
    public JCacheManagerCustomizer jCacheManagerCustomizer() {
        return cacheManager -> {
            createCache(cacheManager, USER_BY_LOGIN, 3600, 100);
            createCache(cacheManager, DISTRICT_BY_REGION_ID, 3600, 100);
            createCache(cacheManager, DISTRICT_BY_DISTRICT_ID, 3600, 100);
            createCache(cacheManager, REGION_BY_REGION_ID, 3600, 100);

            createCache(cacheManager, User.class.getName(), 3600, 100);
            createCache(cacheManager, Region.class.getName(), 3600, 100);
            createCache(cacheManager, District.class.getName(), 3600, 100);
            createCache(cacheManager, Neighbourhood.class.getName(), 3600, 100);
            createCache(cacheManager, Company.class.getName(), 3600, 100);
            createCache(cacheManager, Unit.class.getName(), 3600, 100);
            createCache(cacheManager, Menu.class.getName(), 3600, 100);

        };
    }

    private void createCache(javax.cache.CacheManager cacheManager, String cacheName, long ttlSeconds, long heapSize) {
        javax.cache.Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cacheManager.createCache(
                    cacheName,
                    Eh107Configuration.fromEhcacheCacheConfiguration(
                            CacheConfigurationBuilder
                                    .newCacheConfigurationBuilder(
                                            Object.class,
                                            Object.class,
                                            ResourcePoolsBuilder.heap(heapSize)
                                    )
                                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(java.time.Duration.ofSeconds(ttlSeconds)))
                                    .build()
                    )
            );
        }
    }
}
