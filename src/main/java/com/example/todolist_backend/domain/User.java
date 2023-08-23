package com.example.todolist_backend.domain;

import com.example.todolist_backend.dto.user.UserJoinRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;


@Builder  // 객체 생성 패턴 제공 // 생성자에 원하는 필드만 설정하여 객체 생성 -> 가독성 좋아짐, 코드작성 편리
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@Getter
@Setter
@Entity // 해당 클래스를 테이블로 인식할 수 있도록 만드는 어노테이션
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 생성해주는 어노테이션
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createAt;

    // 유저 입장에서는 유저는 1 todo 는 여러개
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // getToDo 를 할때 조인방식
    private List<ToDo> todos;


    // JPA 양방향 순환참조 에러 - JSON 으로 직렬화 할때에 에러
    // 로그인 시 user 정보를 응답데이터로 전달하는데 user 의 todos 에 todos 가 참조하는 user 데이터가 조인되고, 그 user 데이터의 todos 가 참조 또 todos 의 user 가 참조... -> 무한반복 에러
    // 해결방법 1. @JsonIgnore - 직렬화시(Entity 를 Json 으로 변환시) 해당 필드를 무시하므로 JSON 직렬화 할때 데이터가 전달이 안됨
    // 해결방법 2. DTO 반환 - Entity 를 직접 반환하는 것이 아니라 DTO(Entity 와 동일한 형태) 를 반환
}