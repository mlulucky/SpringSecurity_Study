package com.example.todolist_backend.dto.user.response;

import com.example.todolist_backend.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateResponse (
        @Schema(description="회원 정보 수정 여부", example = "true")
        boolean result,
        @Schema(description = "회원 이름", example = "문")
        String name
){
    public UserUpdateResponse res(boolean result, User user) {
        return new UserUpdateResponse(result, user.getName());
    }
}
