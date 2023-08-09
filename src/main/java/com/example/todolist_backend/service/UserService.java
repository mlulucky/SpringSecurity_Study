package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.repository.UserRepsoitory;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepsoitory userRepsoitory;

    public UserService(UserRepsoitory userRepsoitory) {
        this.userRepsoitory = userRepsoitory;
    }

    public String join(User user) {
//    public String join(String email, String password) {
        // 회원이메일 중복 체크
        validateDuplicateUser(user);

        // 저장
        User member = User.builder()
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .build();
         userRepsoitory.save(member);

        // userRepsoitory.save(user);
        // return user.getId();
        return "SUCCESS";
    }

    private void validateDuplicateUser(User user) {
        userRepsoitory.findByEmail(user.getEmail())
                .ifPresent(member -> { throw new IllegalStateException(user.getEmail() + "는 이미 있습니다.");
                });
    }

    public Optional<User> findOne(Long userId) {
        return userRepsoitory.findById(userId);
    }



}
