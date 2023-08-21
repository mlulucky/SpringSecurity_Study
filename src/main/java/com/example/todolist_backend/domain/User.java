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

//    public User(UserJoinRequest dto) {
//        this.account = dto.getAccount();
//        this.userName = dto.getUserName();
//        this.email = dto.getEmail();
//        this.password = dto.getPassword();
//    }

}
