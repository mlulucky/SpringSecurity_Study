package com.example.todolist_backend.repository;

import com.example.todolist_backend.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
      Optional<RefreshToken> findByUserId(Long id);
      Optional<RefreshToken> findByUserIdAndReissueCountLessThan(Long id, long reissuecount);
}
