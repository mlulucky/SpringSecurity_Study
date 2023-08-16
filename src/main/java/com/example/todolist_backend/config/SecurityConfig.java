package com.example.todolist_backend.config;
import com.example.todolist_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

// 🌈 기존 설정에서 deprecated 된 것들이 많아서 찾으면서 했는데, 바뀐게 너무 많다.(거의 다)
@Configuration // 스프링 설정파일
@EnableWebSecurity // 모든 api 에 spring security 인증이 필요하다고 설정 // Spring Security 설정 활성화 // Spring Security 의 기능을 사용하려는 클래스에 적용
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(withDefaults()) // HTTP Basic _ 토큰 인증 // withDefaults() 기본설정
                .csrf(CsrfConfigurer::disable) // csrf 보호 비활성화 // rest api 서버에서는 csrf를 disable // JWT 같은 토큰을 이용하는 Api 서버 용도 라면 csrf 에 안전
                .cors(CorsConfigurer::disable) // cors 보호 비활성화 // cors 는 교차출처(다른출처) 리소스 공유 정책을 설정 // post 가 정상적으로 수행되지 않는다. 그래서 disable() 해야함
                .authorizeHttpRequests(requests -> // HTTP 요청에 대한 인증 및 접근 권한을 설정
                        requests.requestMatchers("/","/api/user/join", "/api/user/login").permitAll()  // requestMatchers 의 인자로 전달된 url 은 모두에게 허용(permit.All())
                                .anyRequest().authenticated()	// 그 외의 모든 요청은 인증 필요
                )
                .sessionManagement(sessionManagement -> // 세션 관리 설정을 구성
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않으므로 STATELESS 설정
                )
                .addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class) // 🌈 순서중요) JWT 필터로 인증 후 UserName, Password 로 로그인 인증처리 // jwt TokenFilter
                .build();
                //  JwtFilter : 사용자의 요청에서 JWT를 추출하고, 해당 JWT를 통해 사용자 인증 및 권한 부여를 처리하는 역할 // Spring Security의 OncePerRequestFilter를 확장하여 구현
                //  UsernamePasswordAuthenticationFilter : Spring Security에서 기본적으로 제공하는 사용자 인증 필터 중 하나로, 사용자의 아이디와 비밀번호로 로그인 인증을 처리하는 역할
    }
}
