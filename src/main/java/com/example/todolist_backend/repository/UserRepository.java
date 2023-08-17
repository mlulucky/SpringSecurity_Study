package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 스프링부트가 데이터베이스에 접근할 수 있도록 도와주는 컴포넌트 스캔 어노테이션
public interface UserRepository extends JpaRepository<User, Long> { // JpaRepository<엔티티, 엔티티 pk 타입> : 엔티티를 매개체로 데이터베이스와 통신
    Optional<User> findByUserName(String userName);
    Optional<User> findByAccount(String account);
    boolean existsByAccount(String account);


}
