package com.example.todolist_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

// JPA : 객체관계맵핑 - 객체 & db 테이블 간의 연결

@Builder //  // 객체 생성 패턴 제공 // 생성자에 원하는 필드만 설정하여 객체 생성 -> 가독성 좋아짐, 코드작성 편리 // builder 쓰려면 NoArgsConstructor, AllArgsConstructor 둘다 있어야 함
@Table(name = "todo") // 테이블 정보 지정 // todo 테이블과 맵핑됨 // 기본적으로 클래스 이름을 테이블 이름으로 사용하지만, @Table 어노테이션을 사용하여 다른 이름을 지정할 수 있다.
@Entity // db 테이블에 맵핑되는 자바 클래스
@NoArgsConstructor // 파라미터 없는 기본 생성자 자동으로 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@Getter // 필드의 getter 메서드 자동 생성
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)") // TINYINT : 0, 1 설정시 true, false 로 변환
    private boolean done; // jpa 에서 boolean 은 bit 타입으로 저장됨

    // todo 입장에서는 todo는 여러개 user 는 1
    @ManyToOne
    @JoinColumn(name = "user_id") // user_id 라는 이름으로 todo 테이블에 필드 생성
    private User user;
}
