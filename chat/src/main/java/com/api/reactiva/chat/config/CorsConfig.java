package com.api.reactiva.chat.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Permite temporalmente cualquier origen para confirmar el fallo de CORS
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
