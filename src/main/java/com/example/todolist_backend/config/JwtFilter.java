package com.example.todolist_backend.config;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.service.TokenProvider;
import com.example.todolist_backend.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Request 가 들어왔을 때 Request Header 의 Authorization 필드의 Bearer Token 을 가져옴
    // 가져온 토큰을 검증하고 검증 결과를 SecurityContext 에 추가
    private final TokenProvider tokenProvider;

    // 🌈 doFilterInternal - jwt 권한 부여
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String accessToken = parseBearerToken(request);
            if(accessToken !=null && !accessToken.equalsIgnoreCase("null")) { // 토큰이 있으면
                String userId = tokenProvider.validate(accessToken);
                // SecurityContext 에 추가할 객체 //  사용자 인증 객체를 생성 (사용자식별정보, 패스워드정보, 사용자 권한정보)
                AbstractAuthenticationToken authenticationToken = createAuthenticationToken(userId,  accessToken, request);
                // SecurityContext 에 AbstractAuthenticationToken 객체를 추가해서 해당 Thread 가 지속적으로 인증 정보를 가질수 있도록 해줌
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (ExpiredJwtException e) {
            reissueAccessToken(request, response, e);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response); // request 가 인증이 되면 response 를 응답
    }

    // Request Header 의 Authorization 필드의 Bearer Token 을 가져오는 메서드
    // private String parseBearerToken(HttpServletRequest request, String headerName) { // http header 의 토큰을 가져오기
     private String parseBearerToken(HttpServletRequest request) { // http header 의 토큰을 가져오기

       String bearerToken = request.getHeader("Authorization");
        // hasText : 빈값 || null 인 경우 false 반환
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // substring(n) : 인덱스가 n 이후인 값 반환
        }
        return null;
    }

    // 리프레시 토큰 + 만료된 액세스 토큰 -> 액세스 토큰 발급 + 사용자 인증 => 응답헤더에 새로운 액세스 토큰 반환
    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        try{
            String refreshToken = parseBearerToken(request);
            if(refreshToken == null) {
                throw exception;
            }
            String oldAccessToken = parseBearerToken(request);
            tokenProvider.validateRefreshToken(refreshToken, oldAccessToken);
            String newAccessToken = tokenProvider.recreateAccessToken(oldAccessToken);
            String account = tokenProvider.validate(newAccessToken);
            AbstractAuthenticationToken authenticationToken = createAuthenticationToken(account, newAccessToken, request);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(securityContext);

            response.setHeader("New-Access-Token", newAccessToken);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }

    }

    private  AbstractAuthenticationToken createAuthenticationToken(String account, String token, HttpServletRequest request) {
        AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, token, AuthorityUtils.NO_AUTHORITIES);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }


}