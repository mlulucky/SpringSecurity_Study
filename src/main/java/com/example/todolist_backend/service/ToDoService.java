package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.ToDo;
import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.repository.ToDoRepository;
import com.example.todolist_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoService {

    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;

    public ToDoService(UserRepository userRepository, ToDoRepository toDoRepository) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
    }

    // todo 생성 메서드
    public ToDoCreateResponse createToDo(ToDoCreateRequest dto) { // todo 생성 메서드
        // User 불러오기  // 어떤유저가 작성했는지 user id 로 User 불러오기 -> UserRepository 필요
        Optional<User> userOptional = userRepository.findById(dto.getUserId()); // User 가 존재하지 않을수도 -> 예외를 던지지 않는다. // User 객체 또는 null // Optional - 값이 있을 수도 있고 없을 수도 있는

        // todoEntity 만들기
        ToDo todo = ToDo.builder()
                .content(dto.getContent())
                .done(dto.isDone())
                .user(userOptional.get())
                .build();

        // db 저장 -> todo 를 리턴해줌
        ToDo savedToDo = toDoRepository.save(todo);

        // 생성된 todo 반환 // return new ToDoCreateResponse();
        return ToDoCreateResponse.builder()
                .content(savedToDo.getContent())
                .done(savedToDo.isDone())
                .message("투두 등록이 성공했습니다.")
                .build();
    }
}
