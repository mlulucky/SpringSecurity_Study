//package com.example.todolist_backend.swagger;
//
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import io.swagger.v3.oas.models.OpenAPI;
// @Configuration
public class SwaggerConfig {
//    private static final String SECRUITY_SCHEME_NAME = "authorization";
//
//    @Bean
//    public OpenAPI swaggerApi() { // OpenAPI - springdoc-openapi-starter-webmvc-ui 라이브러리 에서 swagger api 제공
//        return new OpenAPI()
//                .components(new Components()
//                .addSecuritySchemes(
//                        SECRUITY_SCHEME_NAME, new SecurityScheme()
//                        .name(SECRUITY_SCHEME_NAME)
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme("mluck")
//                        .bearerFormat("JWT")
//                ))
//                .addSecurityItem(new SecurityRequirement().addList(SECRUITY_SCHEME_NAME)) // 시큐리티
//                .info(new Info().title("스프링시큐리티 + JWT"));  // 시큐리티 요구 사항을 스웨거에 추가
//    }

}
