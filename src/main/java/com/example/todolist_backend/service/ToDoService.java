package com.example.todolist_backend.service;

import com.example.todolist_backend.domain.ToDo;
import com.example.todolist_backend.domain.User;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.todo.ToDoDTO;
import com.example.todolist_backend.repository.ToDoRepository;
import com.example.todolist_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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
                    .user(userOptional.orElse(null)) // 예외를 던지지 않고 사용자가 존재하지 않는 경우에 대한 예외 처리 // user 가 찾은 사용자로 설정되거나 userOptional 이 비어 있으면 null 로 설정
                    .build();

        try {
            // db 저장 -> todo 를 리턴해줌
            ToDo savedToDo = toDoRepository.save(todo);

            // 생성된 todo 반환 // return new ToDoCreateResponse();
            return ToDoCreateResponse.builder()
                    .id(savedToDo.getId())
                    .content(savedToDo.getContent())
                    .done(savedToDo.isDone())
                    .message("투두 등록이 성공했습니다.")
                    .build();

        }catch (Exception error) {
            return ToDoCreateResponse.builder()
                    .message("투두 등록 중에 문제가 발생했습니다. 다시 시도해주세요.")
                    .build();
        }
    }

    // todo 리스트
    // JPA 양방향 순환참조 에러 처리위해 엔티티대신 DTO 로 치환 -> 컨트롤러에서 list 요청 시 서비스, List<ToDo> 의 ToDo 는 JPA 엔티티로, user 데이터가 담겨서 순환참조 에러 발생되므로 ->  ToDoDTO 로 다시 변경함.
    public List<ToDoDTO> list(Long uId) {
            List<ToDo> entityList = toDoRepository.findByUser_Id(uId);
            if(entityList.isEmpty()) {
                return Collections.emptyList();
            }
            List<ToDoDTO> todos = new ArrayList<>();
            for(ToDo todo : entityList) {
                todos.add(ToDoDTO.convertDTO(todo));
            }
            return todos;
    }

    // todo 내용 수정
    @Transactional
    public ToDoDTO modifyToDo(ToDoDTO toDoDTO) {
        // JPA - update - 변경감지 // 엔티티 (수정-> 저장) => 변경감지로 update 쿼리 실행
        ToDo updateToDo = toDoRepository.findById(toDoDTO.getId()).orElse(null);

        if(updateToDo == null) {
            throw new RuntimeException("업데이트하려는 할일을 찾을 수 없습니다.");
        }
        // 빌더를 사용하여 엔티티 수정
        updateToDo = ToDo.builder()
                .id(toDoDTO.getId())
                .content(toDoDTO.getContent())
                .user(userRepository.findById(toDoDTO.getUserId()).orElse(null))
                .done(toDoDTO.isDone())
                .build();
        // 수정된 엔티티를 저장때
        try{
            toDoRepository.save(updateToDo);
        }catch (Exception e) {
            throw new RuntimeException("할일을 업데이트하는 도중 오류가 발생했습니다.");
        }
        return ToDoDTO.convertDTO(updateToDo);
    }

    // todo 내용 삭제
    @Transactional
    public void deleteToDo(Long id) {
        ToDo todo = toDoRepository.findById(id).orElse(null);
        if(todo == null) {
            throw new RuntimeException("삭제하려는 할일을 찾을 수 없습니다.");
        }
        try {
            toDoRepository.delete(todo);
        } catch (Exception e) {
            throw new RuntimeException("할일을 삭제하는 도중 오류가 발생했습니다.");
        }
    }

}
