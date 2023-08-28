package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    // Optional<User> findByAccount(String account);
    boolean existsByAccountAndPassword(String account, String password); // 인터페이스는 기본적으로 public 접근제어자를 가지므로, 명시적으로 public 사용 불필요
    boolean existsByAccount(String account);
    Optional<User> findByAccount(String account);
//    User findByAccount(String account);






}
