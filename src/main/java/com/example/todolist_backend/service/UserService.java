package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public String join(UserJoinRequest dto) {

        // 🌈 userName 중복 체크 -> db 에서 확인 필요 -> Repository 필요 // findByUserName 는 기본 메서드가 아니라서 Repository 에 메서드 만들기
        userRepository.findByUserName(dto.getUserName())
                .ifPresent(user -> { throw new RuntimeException(dto.getUserName() + "는 이미 있습니다.");
                }); // 유저가 있으면 -> 에러처리

        // 저장
        User user = User.builder()
                .account(dto.getAccount())
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        userRepository.save(user);


        return "SUCCESS";

    }
}
