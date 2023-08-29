package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
//public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
     // Optional<RefreshToken> findByAccountAndReissueCountLessThan(String account, long count);
      Optional<RefreshToken> findByUserIdAndReissueCountLessThan(int id, long count);
//      Optional<RefreshToken> findByUserIdAndReissueCountLessThan(Long id, long count);
}
