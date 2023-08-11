package com.example.todolist_backend.controller;

import com.example.todolist_backend.TodoListBackendApplication;
import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.exception.AppException;
import com.example.todolist_backend.exception.ErrorCode;
import com.example.todolist_backend.service.ToDoService;
import com.example.todolist_backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest // 컨트롤러와 관련된 빈들만 생성되며, 서비스 빈들은 생성되지 않는다. 이 경우, @MockBean 을 사용하여 ToDoService 의 동작을 모킹해야함 // 애플리케이션의 웹 레이어 테스트
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean // Mock 객체를 생성하고 빈으로 등록 // 가짜(Mock) 객체 -> 실제 객체의 동작을 흉내내거나, 시뮬레이션, 🌈 특정메서드 호출 가능
    UserService userService;

    @Autowired
    ObjectMapper objectMapper; // Jackson 라이브러리 ObjectMapper 객체 // java object -> json object

    @Test
    @DisplayName("회원가입 성공")
    void join() throws Exception {
        // writeValueAsBytes : Java 객체를 JSON 형식의 바이트 배열로 직렬화 ( http 통신에서는 요청/응답을 이진데이터 _ 바이트 형식으로 인코딩되어 전송 )
        // mockMvc.perform().andDo().andExpect()
        // andDo() : 테스트 결과 및 요청과 응답 정보를 출력
        // andExpect() : HTTP 응답 상태 코드가 200 OK 인지를 확인
        String account = "userId";
        String userName = "eunjeong";
        String email = "dmswjdans";
        String password = "1111";
        UserJoinRequest request = new UserJoinRequest(account, userName, email, password);

        mockMvc.perform(
            post("/api/user/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)) // 🌈 JSON 직렬화 역직렬화 변환시 기본생성자(매개변수 없는) 필요 -> UserJoinRequest 클래스에 @NoArgsConstructor 필요
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패")
    void join_fail() throws Exception {
        String account = "userId";
        String userName = "eunjeong";
        String email = "dmswjdans";
        String password = "1111";

        when(userService.join(any()))
            .thenThrow(new AppException(ErrorCode.USERNAME_DUPLICATED, ""));

        mockMvc.perform(
            post("/api/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(account, userName, email, password)))
            )
            .andDo(print())
            .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() throws Exception {
        String account = "userId";
        String password = "1111";

//        when(userService.login(any()))
//                .thenReturn("token");

        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(account, password)))
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("로그인 실패 ")
    void login_fail() throws Exception {
        String account = "userId";
        String password = "1111";

//        when(userService.login(any()))
//                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(account, password)))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
