package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
      Optional<RefreshToken> findByUserIdAndReissueCountLessThan(Long id, long count);
}
