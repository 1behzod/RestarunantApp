package uz.behzod.RestarunantApp.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EntityScan(basePackages = {"uz.behzod.RestaurantApp.domain"})
@EnableJpaRepositories(basePackages = {"uz.behzod.RestarunantApp.repository"})

public class DatabaseConfiguration {
    
}
