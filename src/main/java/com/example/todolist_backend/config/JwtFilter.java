package com.example.todolist_backend.config;

import com.example.todolist_backend.service.TokenProvider;
import com.example.todolist_backend.service.UserService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter { // 토큰이 있는지 체크

    // private final UserService userService;
    // private final String key;

    // Request 가 들어왔을 때 Request Header 의 Authorization 필드의 Bearer Token 을 가져옴
    // 가져온 토큰을 검증하고 검증 결과를 SecurityContext 에 추가

    private final TokenProvider tokenProvider;

    // 🌈 doFilterInternal - jwt 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*// account Token 에서 꺼내기
        String account = "";
        // 권한 부여 //  사용자의 계정, 비밀번호 (null로 지정), 그리고 권한을 설정, 여기서는 "USER" 권한을 할당
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, List.of(new SimpleGrantedAuthority("USER")));
        // Detail
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken); // authenticationToken 을 SecurityContextHolder 에 저장 -> Spring Security 는 현재 사용자의 인증 정보를 알 수 있게 됨
        filterChain.doFilter(request, response);*/

        try{
            String token = parseBearerToken(request);
            if(token !=null && !token.equalsIgnoreCase("null")) {
                String account = tokenProvider.validate(token);
                // SecurityContext 에 추가할 객체 //  사용자 인증 객체를 생성 (사용자식별정보, 패스워드정보, 사용자 권한정보)
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, AuthorityUtils.NO_AUTHORITIES);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext 에 AbstractAuthenticationToken 객체를 추가해서 해당 Thread 가 지속적으로 인증 정보를 가질수 있도록 해줌
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response); // request 가 인증이 되면 response 를 응답
    }

    // Request Header 의 Authorization 필드의 Bearer Token 을 가져오는 메서드
    private String parseBearerToken(HttpServletRequest request) { // http header 의 토큰을 가져오기
        String bearerToken = request.getHeader("Authorization");
        // hasText : 빈값 || null 인 경우 false 반환
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // substring(n) : 인덱스가 n 이후인 값 반환
        }
        return null;
    }


}
