package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.todo.ToDoCreateRequest;
import com.example.todolist_backend.dto.todo.ToDoCreateResponse;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.service.ToDoService;
import com.example.todolist_backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // JSON 형식의 데이터를 생성하고 응답 헤더를 설정하여 클라이언트에게 전달
@RequestMapping("/api/user")
@RequiredArgsConstructor //  final 필드 또는 @NonNull 어노테이션이 붙은 필드를 대상으로 하는 생성자를 자동으로 생성
@Slf4j
public class UserController {
//    @GetMapping("/login")
//    public ResponseEntity<String> login(){
//        return ResponseEntity.ok("login");
//    }
    private final UserService userService;
    private final ToDoService toDoService;


    // @Service 컴포넌트스캔으로 빈 등록되어, @Autowired 어노테이션 사용하지 않아도 의존성 주입
//    public UserController(ToDoService toDoService, UserService userService) {
//        this.toDoService = toDoService;
//        this.userService = userService;
//    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) { // dto == userJoinRequest
        userService.join(dto);
        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
    }

    @PostMapping("/{id}/todos") // id 는 유저 id
    public ResponseEntity<ToDoCreateResponse> add(@RequestBody ToDoCreateRequest toDoCreateRequest) { // 요청본문으로 들어온 json 을 객체로 맵핑 // toDoCreateRequest - dto
        return ResponseEntity.ok().body(toDoService.createToDo(toDoCreateRequest)); // 컨트롤러가 사용자 요청을 받아, 서비스에 전달
    }


}
