package com.example.todolist_backend.dto.todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 🌈 lombok 어노테이션 사용 - 해당 의존성 모두 추가!
//  compileOnly 'org.projectlombok:lombok:1.18.26'
//	annotationProcessor 'org.projectlombok:lombok:1.18.26'
//	testImplementation 'org.projectlombok:lombok:1.18.26'

@Builder // 객체 생성 패턴 제공 // 생성자에 원하는 필드만 설정하여 객체 생성 -> 가독성 좋아짐, 코드작성 편리
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@NoArgsConstructor // 파라미터 없는 기본 생성자 자동으로 생성
@Getter // 필드의 getter 메서드 자동 생성
public class ToDoCreateRequest { // 사용자 요청 -> db 에 이 내용으로 저장
     private Long userId;
    // private String userName;
    private String content;
    private boolean done;

    public ToDoCreateRequest(String content) {
        this.content = content;
    }





}
