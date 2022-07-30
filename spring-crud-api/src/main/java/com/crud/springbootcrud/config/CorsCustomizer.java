package com.crud.springbootcrud.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

@Component
public class CorsCustomizer {

    public void corsCustomizer(HttpSecurity http) throws Exception {
        http.cors(cors -> {
            CorsConfigurationSource corsConfigurationSource = source -> {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowCredentials(true);
                corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:3000"));
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("*"));
                return corsConfiguration;
            };

            cors.configurationSource(corsConfigurationSource);
        });
    }

}
