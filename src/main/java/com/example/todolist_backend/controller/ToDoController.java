package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.todo.ToDoDTO;
import com.example.todolist_backend.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 웹 어댑터는 일반적으로 다음과 같은 일을 한다.
//HTTP 요청 자바 객체 매핑
//권한 검사
//입력 유효성 검증
//입력을 유스케이스 입력 모델로 매핑
//유스케이스 호출
//유스케이스의 출력을 HTTP로 매핑
//HTTP 응답을 반환
//웹 어댑터의 어깨를 짓누를 정도로 책임이 많기는 하다. 하지만 이 책임은 애플리케이션 계층이 신경 쓰면 안 되는 것들이기도 하다.
//우리가 바깥 계층에서 HTTP를 다루고 있다는 것을 애플리케이션 코어가 알게 되면 HTTP를 사용하지 않는 또 다른 인커밍 어댑터의 요청에 대해 동일한 도메인 로직을 수행할 수 있는 선택지를 잃게 된다.
//웹 어댑터와 애플리케이션 계층의 이 같은 경계는 웹 계층부터 개발을 시작하는 대신, 도메인과 애플리케이션 계층부터 개발하기 시작하면 자연스럽게 생긴다.

//웹 어댑터 = 컨트롤러 , 유스케이스 = 서비스

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("/{uId}/list")
    public ResponseEntity<List<ToDoDTO>> getToDoList(@PathVariable long userId) { // 응답데이터 - List<ToDoDTO>
        List<ToDoDTO> todos = toDoService.list(userId);
        return ResponseEntity.ok().body(todos);
    }

    @PostMapping("/register")
    public ResponseEntity<ToDoCreateResponse> register(@RequestBody ToDoCreateRequest toDoCreateRequest) { // utoDoCreateRequest 를 응답 body 에 받겠다.
        try {
            long todoId = toDoService.createToDo(toDoCreateRequest);
            ToDoCreateResponse response = ToDoCreateResponse.builder()
                    .id(todoId)
                    .message("할일이 등록되었습니다.")
                    .build();
            return ResponseEntity.ok().body(response);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/modify")
    public ResponseEntity<ToDoDTO> modify(@RequestBody ToDoDTO toDoDTO) {
        return ResponseEntity.ok().body(toDoService.modifyToDo(toDoDTO)); // 응답본문에 포함
    }

    @DeleteMapping("/{todoId}/delete")
    public ResponseEntity<Void> delete(@PathVariable long todoId) {
        toDoService.deleteToDo(todoId);
        return ResponseEntity.ok().build(); // 상태코드만 반환
    }

    // security 인증 - 사용자 확인 테스트
    @GetMapping("/usercheck") // @AuthenticationPrincipal : Spring Security에서 현재 사용자의 인증(principal) 정보를 추출 // jwtFilter 에 SecurityContext 에 인증할 객체로 account 를 담았기때문에, account 정보를 가져와 쓸수있다.
    public String getToDo(@AuthenticationPrincipal String account) { // jwtFilter 에 SecurityContext 에 인증할 객체로 account 를 담았기때문에, account 정보를 가져와 쓸수있다.
        return "로그인된 사용자는 " + account + "입니다.";
    }

}
