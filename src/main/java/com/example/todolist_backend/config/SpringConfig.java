package com.example.todolist_backend.config;

import com.example.todolist_backend.repository.UserRepository;
import com.example.todolist_backend.service.AuthService;
import com.example.todolist_backend.service.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class SpringConfig {
//    @Bean
//    public WebMvcConfigurer corsConfigurer(){ // cors 예외처리
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOriginPatterns("http://localhost:3000");
//            }
//        };
//    }
//}
