package com.example.todolist_backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Table(name = "todo")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String text;
    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)") // TINYINT : 0, 1 설정시 true, false 로 변환
    private boolean done; // jpa 에서 boolean 은 bit 타입으로 저장됨

    // todo 입장에서는 todo는 여러개 user 는 1
    @ManyToOne
    @JoinColumn(name = "user_id") // user_id 라는 이름으로 todo 테이블에 필드 생성
    private User user;
}
