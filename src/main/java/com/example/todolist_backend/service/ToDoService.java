package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.ToDo;
import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.todo.ToDoDTO;
import com.example.todolist_backend.repository.ToDoRepository;
import com.example.todolist_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ToDoService {
    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;

    // todo 생성 메서드
    public long createToDo(ToDoCreateRequest dto) { // todo 생성 메서드
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        // todoEntity 만들기
        ToDo todo = ToDo.builder()
                .content(dto.getContent())
                .done(dto.isDone())
                .user(user)
                .build();

        // db 저장 -> 에러처리는 컨트롤러에서
        ToDo savedToDo = toDoRepository.save(todo);
        return todo.getId();
    }

    // todo 리스트
    // JPA 양방향 순환참조 에러 처리위해 엔티티대신 DTO 로 치환 -> 컨트롤러에서 list 요청 시 서비스, List<ToDo> 의 ToDo 는 JPA 엔티티로, user 데이터가 담겨서 순환참조 에러 발생되므로 ->  ToDoDTO 로 다시 변경함.
    public List<ToDoDTO> list(long userId) { // long 타입 - null 방지
        List<ToDo> entityList = toDoRepository.findByUser_Id(userId);
        if (entityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ToDoDTO> todos = new ArrayList<>();
        for (ToDo todo : entityList) {
            todos.add(ToDoDTO.convertToDoDTO(todo));
        }
        return todos;
    }

    // todo 내용 수정
    @Transactional
    public ToDoDTO modifyToDo(ToDoDTO toDoDTO) {
        // JPA - update - 변경감지 // 엔티티 (수정-> 저장) => 변경감지로 update 쿼리 실행
        ToDo todo = toDoRepository.findById(toDoDTO.getId()).orElseThrow(()->  new IllegalArgumentException("업데이트하려는 할일을 찾을 수 없습니다."));

        // 엔티티 수정 _ content, done 만 수정 todo = {id, content, done, userId}
        todo.setUpdate(toDoDTO.getContent(), toDoDTO.isDone());
        return ToDoDTO.convertToDoDTO(todo);
    }

    // todo 내용 삭제
    @Transactional
    public void deleteToDo(long id) {
        ToDo todo = toDoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("삭제하려는 할일을 찾을 수 없습니다."));
        toDoRepository.delete(todo);
    }

}
