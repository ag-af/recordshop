package com.northcoders.recordshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("api/v1/open/albums").permitAll();
                    auth.requestMatchers("api/v1/protected/albums", "/swagger-ui/**").authenticated();
                })
                //       .formLogin(withDefaults());
                .oauth2Login();
        return http.build();
    }
}