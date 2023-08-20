package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByAccount(String account);
    boolean existsByAccountAndPassword(String account, String password);
    boolean existsByAccount(String account);
}
