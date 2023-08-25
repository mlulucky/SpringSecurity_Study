package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.ToDo;
import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.todo.ToDoDTO;
import com.example.todolist_backend.repository.ToDoRepository;
import com.example.todolist_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
                .user(userOptional.orElse(null)) // user 가 찾은 사용자로 설정되거나 userOptional 이 비어 있으면 null 로 설정
//                .user(userOptional.get()) // 투두 작성한 유저
                .build();

        // db 저장 -> todo 를 리턴해줌 // 🎄 repository 코드 - try catch 적용하기
        ToDo savedToDo = toDoRepository.save(todo);

        // 생성된 todo 반환 // return new ToDoCreateResponse();
        return ToDoCreateResponse.builder()
                .content(savedToDo.getContent())
                .done(savedToDo.isDone())
                .message("투두 등록이 성공했습니다.")
                .build();
    }

    // todo 리스트
    // JPA 양방향 순환참조 에러 처리위해 엔티티대신 DTO 로 치환 -> 컨트롤러에서 list 요청 시 서비스, List<ToDo> 의 ToDo 는 JPA 엔티티로, user 데이터가 담겨서 순환참조 에러 발생되므로 ->  ToDoDTO 로 다시 변경함.
    public List<ToDoDTO> list(Long uId) {
        List<ToDo> entityList = toDoRepository.findByUser_Id(uId);
        List<ToDoDTO> todos = new ArrayList<>();
        for(ToDo todo : entityList) {
            todos.add(ToDoDTO.convertDTO(todo));
        }
        return todos;
    }

}
