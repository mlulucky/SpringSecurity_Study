package com.example.todolist_backend.controller;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.repository.UserRepository;
import com.example.todolist_backend.service.AuthService;
import com.example.todolist_backend.service.ToDoService;
import com.example.todolist_backend.service.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 @WebMvcTest
class ToDoControllerTest {
    @Autowired
    MockMvc mockMvc; // 서블릿, WAS(톰캣) 구동없이 모의 HTTP 서블릿 요청을 전송하는 기능을 제공하는 유틸리티

    @MockBean
    ToDoService toDoService;
    @MockBean
    AuthService authService;
    @MockBean
    TokenProvider tokenProvider;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("투두등록 성공")
    // @WithMockUser(username = "testUser", password = "testPassword", authorities = "ROLE_USER")
    @WithMockUser
    void add() throws Exception {
        // long userId = 1;
        String content = "투두등록 테스트";
        String account = "user111";
        String password = "1234";

         UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String userId = userDetails.getUsername();

        User user = userRepository.findByAccount(account);

        ToDoCreateRequest request = new ToDoCreateRequest(content);

        mockMvc.perform(
        MockMvcRequestBuilders.get("/api/todo/")
//        post("/api/todo/1/todos")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsBytes(request))
        )
        .andDo(print())
        .andExpect(status().isOk());





    }







}