package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(String token);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tbl_reset_password_token WHERE token = ?1", nativeQuery = true)
    void deleteByToken(String token);
}
