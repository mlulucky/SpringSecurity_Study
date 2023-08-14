package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.service.ToDoService;
import com.example.todolist_backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식의 데이터를 생성하고 응답 헤더를 설정하여 클라이언트에게 전달
@RequestMapping("/api/user")
@RequiredArgsConstructor // 🌈 final 필드 또는 @NonNull 어노테이션이 붙은 필드를 대상으로 하는 생성자를 자동으로 생성
public class UserController {

    private final UserService userService;
  //  private final ToDoService toDoService;

    // 🌈 @RequiredArgsConstructor 으로 final 필드 생성자 자동 생성
//    public UserController(ToDoService toDoService, UserService userService) {
//        this.toDoService = toDoService;
//        this.userService = userService;
//    }

    // ResponseEntity : HTTP 응답을 표현하기 위한 클래스, 클라이언트에게 응답을 생성하고 반환(응답의 상태 코드, 헤더, 본문 등을 정의할 수 있다)
    // <String>: ResponseEntity 가 응답 본문으로 문자열(String) 데이터를 지정. 응답 본문은 클라이언트에게 전달될 데이터
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) { // dto == userJoinRequest
        userService.join(dto);
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

//    @PostMapping("/{id}/todos") // id 는 유저 id
//    public ResponseEntity<ToDoCreateResponse> add(@RequestBody ToDoCreateRequest toDoCreateRequest) { // 요청본문으로 들어온 json 을 객체로 맵핑 // toDoCreateRequest - dto
//        return ResponseEntity.ok().body(toDoService.createToDo(toDoCreateRequest)); // 컨트롤러가 사용자 요청을 받아, 서비스에 전달
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto) {
        String token = userService.login(dto.getAccount(), dto.getPassword());
        return ResponseEntity.ok().body(token); // ResponseEntity 클래스 : HTTP 응답을 생성하는 코드, 성공적인 상태일 때 200 OK 상태 코드와 함께 응답 본문(body)에 token 값을 담아서 클라이언트에게 반환하겠다
    }
}
