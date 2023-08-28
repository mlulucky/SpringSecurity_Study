package com.example.todolist_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 접근제어 - protected
@Getter
public class RefreshToken { // 리프레시 토큰 테이블
    @Id
    private Long userId;
    private String refreshToken;
    private int reissueCount = 0; // ??

    public RefreshToken(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public boolean validateRefreshToken(String refreshToken) {
        return this.refreshToken.equals(refreshToken);
    }
    public void increaseReissueCount(){
        reissueCount++;
    }
    @OneToOne(fetch = FetchType.LAZY) // 회원 1명 당 리프레시토큰 1개
    @MapsId // 연관 엔티티의 primary key 를 현재 엔티티의 primary 키와 맵핑. primary 키를 공유
    @JoinColumn(name = "user_id")
    private User user;
}
