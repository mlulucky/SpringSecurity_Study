package com.example.todolist_backend.config;

import com.example.todolist_backend.service.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // 토큰이 있는지 체크

    private final UserService userService;
    private final String key;

    // 🌈 doFilterInternal - jwt 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // account Token 에서 꺼내기
        String account = "";
        // 권한 부여 //  사용자의 계정, 비밀번호 (null로 지정), 그리고 권한을 설정, 여기서는 "USER" 권한을 할당
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, List.of(new SimpleGrantedAuthority("USER")));
        // Detail
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // authenticationToken 을 SecurityContextHolder 에 저장 -> Spring Security 는 현재 사용자의 인증 정보를 알 수 있게 됨
        filterChain.doFilter(request, response);
    }
}
