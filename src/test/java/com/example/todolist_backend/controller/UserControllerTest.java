package com.example.todolist_backend.controller;

import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.repository.UserRepsoitory;
import com.example.todolist_backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper; // Jackson // java object -> json object
    @Test
    void 회원가입() {
        mockMvc.perform(post("/api/user/join"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeVal)
    }
}