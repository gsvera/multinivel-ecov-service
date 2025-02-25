package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.StatusBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatusBuyRepository extends JpaRepository<StatusBuy, Long> {
    @Query(value = "SELECT * FROM tbl_status_buy WHERE order_status = ?1", nativeQuery = true)
    StatusBuy findByOrderStatus(int orderStatus);
}
