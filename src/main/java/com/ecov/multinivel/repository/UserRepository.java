package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_users SET token = ?2 WHERE id = ?1", nativeQuery = true)
    int updateTokenById(String id, String token);

    @Transactional @Modifying
    @Query(value = "UPDATE tbl_users SET token = '' WHERE token = ?1", nativeQuery = true)
    int updateToken(String token);

    @Query(value = """
            WITH RECURSIVE user_hierarchy AS (
                        SELECT
                            id,
                            first_name,
                            last_name,
                            reference_parent,
                            created_date,
                            0 AS nivel
                        FROM tbl_users
                        WHERE reference_parent IS NULL
                        
                        UNION ALL
                        
                        SELECT
                            u.id,
                            u.first_name,
                            u.last_name,
                            u.reference_parent,
                            u.created_date,
                            uh.nivel + 1
                        FROM tbl_users u
                        INNER JOIN user_hierarchy uh ON u.reference_parent = uh.id
                    )
                    SELECT * FROM user_hierarchy ORDER BY nivel
            """, nativeQuery = true)
    List<Object[]> findUserHierarchy();
    @Query(value = """
            WITH RECURSIVE user_hierarchy AS (
                        SELECT
                            id,
                            first_name,
                            last_name,
                            reference_parent,
                            created_date,
                            0 AS nivel
                        FROM tbl_users
                        WHERE id = ?1
                        
                        UNION ALL
                        
                        SELECT
                            u.id,
                            u.first_name,
                            u.last_name,
                            u.reference_parent,
                            u.created_date,
                            uh.nivel + 1
                        FROM tbl_users u
                        INNER JOIN user_hierarchy uh ON u.reference_parent = uh.id
                    )
                    SELECT * FROM user_hierarchy ORDER BY nivel
            """, nativeQuery = true)
    List<Object[]> findUserByUserId(String userId);
    @Query(value = "SELECT * FROM tbl_users WHERE workgroup_id = ?1 AND (?2 IS NULL OR first_name ILIKE %?2% OR last_name ILIKE %?2% OR email ILIKE %?2%)", nativeQuery = true)
    Page<User> findAllAffiliate(int workgroupId, String word, Pageable pageable);
}
