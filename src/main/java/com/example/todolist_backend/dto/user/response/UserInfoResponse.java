package com.example.todolist_backend.dto.user.response;

import com.example.todolist_backend.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoResponse(
    UUID id,
    String account,
    String email,
    String name,
    LocalDateTime createAt
) {
    public static UserInfoResponse res(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getAccount(),
                user.getEmail(),
                user.getName(),
                user.getCreateAt()
        );
    }

}
