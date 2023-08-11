package com.example.todolist_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 🌈 순환참조 문제 발생 위험 있어서 EncoderConfig 와 SecurityConfig 는 클래스를 따로 만든다.
@Configuration
public class EncoderConfig { // 비밀번호 인코딩

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();

    }
}
