package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long> {
    @Query(value = "SELECT * FROM tbl_config WHERE label_descript = ?1", nativeQuery = true)
    Optional<Config> findByReference(String reference);
}
