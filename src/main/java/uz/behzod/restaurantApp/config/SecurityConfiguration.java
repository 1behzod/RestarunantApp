package uz.behzod.restaurantApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;

import java.time.Duration;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(List.of("*"));
                    config.setAllowedMethods(List.of("*"));
                    config.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION, HttpHeaders.LINK, "X-Total-Count"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(Duration.ofSeconds(1800));
                    return config;
                }))
                .csrf(CsrfConfigurer::disable)
                .headers(headers ->
                        headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                                .referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/v1/companies/**").permitAll()
                                .requestMatchers("/v1/branches/**").permitAll()
                                .requestMatchers("/v1/departments/**").permitAll()
                                .requestMatchers("/v1/menu-items/**").permitAll()
                                .requestMatchers("/v1/menus/**").permitAll()
                                .requestMatchers("/v1/products/**").permitAll()
                                .requestMatchers("/v1/users/**").permitAll()
                                .requestMatchers("/v1/regions/**").permitAll()
                                .requestMatchers("/v1/districts/**").permitAll()
                                .requestMatchers("/v1/caches/**").permitAll()
                                .requestMatchers("/files/**").permitAll()
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
