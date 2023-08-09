package com.example.todolist_backend.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateRequest( // record 클래스 -
        @Schema(description = "회원 비밀번호", example = "1234")
        String password,
        @Schema(description = "회원 새로운 비밀번호", example = "1234")
        String newPassword,
        @Schema(description = "회원 이름", example = "moon")
        String name
) {

}
