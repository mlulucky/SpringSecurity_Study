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


@Builder  // 객체 생성 패턴 제공 // 생성자에 원하는 필드만 설정하여 객체 생성 -> 가독성 좋아짐, 코드작성 편리
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@Getter
@Entity // 해당 클래스를 테이블로 인식할 수 있도록 만드는 어노테이션
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createAt;

    // 유저 입장에서는 유저는 1 todo 는 여러개
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // getToDo 를 할때 조인방식
    private List<ToDo> todos;

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
