package com.example.todolist_backend.controller;

import com.example.todolist_backend.TodoListBackendApplication;
import com.example.todolist_backend.config.SecurityConfig;
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
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest // 컨트롤러와 관련된 빈들만 생성되며, 서비스 빈들은 생성되지 않는다. 이 경우, @MockBean 을 사용하여 ToDoService 의 동작을 모킹해야함 // 애플리케이션의 웹 레이어 테스트
class UserControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean // Mock 객체를 생성하고 빈으로 등록 // 가짜(Mock) 객체 -> 실제 객체의 동작을 흉내내거나, 시뮬레이션, 🌈 특정메서드 호출 가능
//    UserService userService;
//
//    @Autowired
//    ObjectMapper objectMapper; // Jackson 라이브러리 ObjectMapper 객체 // java object -> json object
//
//    @Test
//    @DisplayName("회원가입 성공")
//    @WithMockUser
//    void join() throws Exception {
//        // writeValueAsBytes : Java 객체를 JSON 형식의 바이트 배열로 직렬화 ( http 통신에서는 요청/응답을 이진데이터 _ 바이트 형식으로 인코딩되어 전송 )
//        // mockMvc.perform().andDo().andExpect()
//        // andDo() : 테스트 결과 및 요청과 응답 정보를 출력
//        // andExpect() : HTTP 응답 상태 코드가 200 OK 인지를 확인
//        String account = "userId";
//        String userName = "eunjeong";
//        String email = "dmswjdans";
//        String password = "1111";
//        UserJoinRequest request = new UserJoinRequest(account, userName, email, password);
//
//        mockMvc.perform(
//            post("/api/user/join")
//            .with(csrf()) // post 요청시에 넣어주기. post 요청 다음에! // 🌈 테스트에서 spring security 에 접근하는 것은 csrf 라고 판단하기 때문에 -> .with(csrf()) 해줘야함 -> spring security test 의존성 설치
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsBytes(request)) // 🌈 JSON 직렬화 역직렬화 변환시 기본생성자(매개변수 없는) 필요 -> UserJoinRequest 클래스에 @NoArgsConstructor 필요
//            )
//            .andDo(print())
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("회원가입 실패 - userName 중복")
//    @WithMockUser
//    void join_fail() throws Exception {
//        String account = "userId";
//        String userName = "eunjeong";
//        String email = "dmswjdans";
//        String password = "1111";
//
//        when(userService.join(any()))
//            .thenThrow(new AppException(ErrorCode.USERNAME_DUPLICATED, ""));
//
//        mockMvc.perform(
//            post("/api/user/join")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(new UserJoinRequest(account, userName, email, password)))
//            )
//            .andDo(print())
//            .andExpect(status().isConflict());
//    }
//
//    @Test
//    @DisplayName("로그인 성공")
//    @WithMockUser // 인증된 사용자로 해당 요청은 인증되었으므로 인가된 상태로 실행 // @WithAnonymousUser를 사용하면 해당 요청은 인증되지 않았으므로 인가되지 않는 에러가 발생
//    // @WithAnonymousUser // 인증되지 않은, 익명 사용자로 특정 메서드를 실행하도록 지정
//    void login_success() throws Exception {
//        String account = "userId";
//        String password = "1111";
//
//        when(userService.login(any(),any())) // any() : 어떤 타입의 인자든지 전달할 수 있음
//                .thenReturn("token");
//
//        mockMvc.perform(post("/api/user/login")
//                .with(csrf())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsBytes(new UserLoginRequest(account, password)))
//        )
//                .andDo(print())
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - userName 없는 경우")
//    @WithMockUser
//    void login_fail() throws Exception {
//        String account = "userId";
//        String password = "1111";
//
//        when(userService.login(any(),any()))
//                .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ""));
//
//        mockMvc.perform(post("/api/user/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(account, password)))
//                )
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("로그인 실패 - password 틀림")
//    @WithMockUser
//    void login_fail2() throws Exception {
//        String account = "userId";
//        String password = "1111";
//
//        when(userService.login(any(), any()))
//                .thenThrow(new AppException(ErrorCode.INVALID_PASSWORD, ""));
//
//        mockMvc.perform(post("/api/user/login")
//                        .with(csrf())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(account, password)))
//                )
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//    }


}
