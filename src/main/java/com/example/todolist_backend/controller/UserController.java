package com.example.todolist_backend.controller;

import com.example.todolist_backend.dto.UserLoginRequest;
import com.example.todolist_backend.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.todolist_backend.dto.user.ResponseDto;
import com.example.todolist_backend.dto.user.UserJoinRequest;
import com.example.todolist_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
//@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController // JSON 형식의 데이터를 생성하고 응답 헤더(본문 body)로 데이터를 클라이언트에게 전달하겠다 // @Controller + @ResponseBody
@RequestMapping("/api/user") // @RequestMapping(url)
@RequiredArgsConstructor // 🌈 final 필드 또는 @NonNull 어노테이션이 붙은 필드를 대상으로 하는 생성자를 자동으로 생성
public class UserController {

    private final UserService userService;

    @Autowired
    private AuthService authService;
  //  private final ToDoService toDoService;

    // 🌈 @RequiredArgsConstructor 으로 final 필드 생성자 자동 생성
//    public UserController(ToDoService toDoService, UserService userService) {
//        this.toDoService = toDoService;
//        this.userService = userService;
//    }

    @GetMapping("/hello")
    public String hello(){
        return "spring 컨트롤러 연결성공";
    }

    ObjectMapper objectMapper = new ObjectMapper();



    // ResponseEntity : HTTP 응답을 표현하기 위한 클래스, 클라이언트에게 응답을 생성하고 반환(응답의 상태 코드, 헤더, 본문 등을 정의할 수 있다)
    // <String>: ResponseEntity 가 응답 본문으로 문자열(String) 데이터를 지정. 응답 본문은 클라이언트에게 전달될 데이터
    @PostMapping("/join")
    public ResponseDto<?> join(@RequestBody UserJoinRequest requestBody) throws JsonProcessingException { // <?> : 타입 와일드카드
        // api 데이터 체크
        // String jsonString = objectMapper.writeValueAsString(requestBody); //  ObjectMapper : 객체를 JSON 문자열로 변환하여 출력
        // System.out.println(jsonString);

        ResponseDto<?> result = authService.join(requestBody);
        return result;
    }
//    public ResponseEntity<String> join(@RequestBody UserJoinRequest dto) { // dto == userJoinRequest
//        userService.join(dto);
//        return ResponseEntity.ok().body("회원가입이 성공했습니다.");
//    }

//    @PostMapping("/{id}/todos") // id 는 유저 id
//    public ResponseEntity<ToDoCreateResponse> add(@RequestBody ToDoCreateRequest toDoCreateRequest) { // @RequestBody 요청본문(body)으로 들어온 json 을 객체로 맵핑 // @ResponseBody 응답을 본문(body)으로 객체 data 를 보내겠다.
//        return ResponseEntity.ok().body(toDoService.createToDo(toDoCreateRequest)); // 컨트롤러가 사용자 요청을 받아, 서비스에 전달
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest dto) {
        String token = userService.login(dto.getAccount(), dto.getPassword()); // 로그인시 토큰발행 (-> 유효한 토큰인지 확인 -> 인증, 인가 기능)
        return ResponseEntity.ok().body(token); // ResponseEntity 클래스 : HTTP 응답을 생성하는 코드, 성공적인 상태일 때 200 OK 상태 코드와 함께 응답 본문(body)에 token 값을 담아서 클라이언트에게 반환하겠다
    }
}
