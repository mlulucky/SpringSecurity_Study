package com.example.todolist_backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


// @Table(name = "users")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 생성자를 자동 생성
@AllArgsConstructor // 파라미터가 있는 생성자를 자동 생성
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String name;
    private String email;
    private String password;

    @CreationTimestamp
    private LocalDateTime createAt;

    public static User user(User user){

        return User.build();
    }

}
// public class User implements UserDetails { // UserDetails : 스프링 시큐리티 _ 사용자 인증정보를 담아두는 인터페이스
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", updatable = false)
//    private Long id;
//
//    @Column(name = "email", nullable = false, unique = true)
//    private String email;
//
//    @Column(name = "password")
//    private String password;
//
//    @Builder
//    public User(String email, String password, String auth) {
//        this.email = email;
//        this.password = password;
//    }
//
//
//    @Override // 권한반환
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("user"));
//    }
//
//    @Override // 사용자의 email 를 반환(고유한 값)
//    public String getUsername() {
//        return email;
//    }
//
//    @Override // 사용자의 패스워드 를 반환
//    public String getPassword() {
//        return password;
//    }
//
//    @Override // 계정 만료여부 반환
//    public boolean isAccountNonExpired() {
//        return true; // true -> 만료되지 않음
//    }
//
//    @Override // 계정 잠금 여부 반환
//    public boolean isAccountNonLocked() {
//        return true; // true -> 잠금되지 않았음
//    }
//
//    @Override // 패스워드의 만료여부 반한
//    public boolean isCredentialsNonExpired() {
//        return true;  // true -> 만료되지 않음
//    }
//
//    @Override // 계정 사용가능여부 반환
//    public boolean isEnabled() {
//        return true; // true -> 사용 가능
//    }
// }
