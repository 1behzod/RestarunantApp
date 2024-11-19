package uz.behzod.restaurantApp.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EntityScan(basePackages = {"uz.behzod.restaurantApp.domain"})
@EnableJpaRepositories(basePackages = {"uz.behzod.restaurantApp.repository"})

public class DatabaseConfiguration {

}
