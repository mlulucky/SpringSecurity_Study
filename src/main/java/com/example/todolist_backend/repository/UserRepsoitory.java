package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepsoitory extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); //email 로 사용자 정보 가져옴 // == FROM users WHERE email = #{email}
    boolean existsByEmail(String email);
}
