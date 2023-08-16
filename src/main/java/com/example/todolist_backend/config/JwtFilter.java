package com.example.todolist_backend.config;

import com.example.todolist_backend.service.UserService;
import com.example.todolist_backend.utils.JwtTokenUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // 🌈 토큰이 있는지 체크

    private final UserService userService;
    private final String secretKey;

    // 🌈 doFilterInternal - jwt 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authroization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authroization : {}", authroization);

        // Token 안보내면
        if(authroization == null || !authroization.startsWith("Bearer ")) {
            log.error("Authroization 을 잘못 보냈습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authroization.split(" ")[1]; // "Bearer 토큰

        // Token Expired 되었는지 여부
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            log.error("Token이 만료 되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // account Token 에서 꺼내기
        String account = JwtTokenUtil.getUserName(token, secretKey);
        log.info("account : {}", account);

        // 권한 부여 //  사용자의 계정, 비밀번호 (null로 지정), 그리고 권한을 설정, 여기서는 "USER" 권한을 할당
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, List.of(new SimpleGrantedAuthority("USER")));
        // Detail
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // authenticationToken 을 SecurityContextHolder 에 저장 -> Spring Security 는 현재 사용자의 인증 정보를 알 수 있게 됨
        filterChain.doFilter(request, response);
    }
}
