package uz.behzod.restaurantApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantAppApplication.class, args);
    }

    //1 CacheConstants
    //2 CacheConfig
    //User service -> 1.create() 2.update() 3.delete() 4.updateStatus()
}
