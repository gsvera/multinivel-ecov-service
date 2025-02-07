package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_users SET token = ?2 WHERE id = ?1", nativeQuery = true)
    int updateTokenById(String id, String token);

    @Transactional @Modifying
    @Query(value = "UPDATE tbl_users SET token = '' WHERE token = ?1", nativeQuery = true)
    int updateToken(String token);
}
