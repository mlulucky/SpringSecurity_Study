package com.example.todolist_backend.config;

import com.example.todolist_backend.service.UserDetailService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor //  Lombok 라이브러리 -> 생성자를 자동으로 생성해주는 어노테이션
@Configuration // 스프링 설정파일
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
                )
                .formLogin(login -> login	// form 방식 로그인 사용
                        .defaultSuccessUrl("/view/dashboard", true)	// 성공 시 dashboard로
                        .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용
                )
                .logout(withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)

        return http.build();
    }


/*    private final UserDetailService userService;

    public WebSecurityConfig(UserDetailService userService) {
        this.userService = userService;
    }

    @Bean
    // 스프링 시큐리티 인증, 인가 서비스 비활성화
    public WebSecurityCustomizer configure(){
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) // h2 데이터 확인
                .requestMatchers("/static/**"); // static 폴더 하위 리소스
    }*/

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable().cors().disable() //  csrf와 cors를 disable 하는 것
//                .authorizeHttpRequests(request -> request
//                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll() // forward 방식 페이지 이동에도 default로 인증이 걸리도록
//                        .anyRequest().authenticated() // 어떠한 요청이라도 인증필요
//                )
//                .formLogin(login -> login // form 방식 로그인 사용
//                        .loginPage("/view/login") // 커스텀 로그인 페이지
//                        .loginProcessingUrl("/login-process") // submit url
//                        .usernameParameter("userid") // submit 아이디
//                        .passwordParameter("pw") // submit 비밀번호
//                        .defaultSuccessUrl("/view/dashboard", true) // 성공 시 dashboard로
//                        .permitAll()
//                )
//                .logout(withDefaults()); // 로그아웃은 기본설정으로 (/logout으로 인증해제)
//        return http.build();
//    }

   /* @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .requestMatchers("/login", "/signup", "/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/articles")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}
