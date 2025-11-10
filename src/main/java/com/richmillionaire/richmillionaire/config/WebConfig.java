package com.richmillionaire.richmillionaire.config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Gestion des fichiers uploadés
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get("upload").toAbsolutePath().toUri().toString();
        
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadPath);
    }

    // Configuration CORS pour autoriser le front Angular et les cookies HttpOnly
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") 
                        .allowCredentials(true)
                        .allowedMethods("*");
            }
        };
    }
}
