package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.PayAffiliate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PayAffiliateRepository extends JpaRepository<PayAffiliate, Long> {
    @Query(value = "SELECT pa.id, pa.amount, pa.created_date, pa.description, pa.status_pay, pa.payment_file ,pa.pay_method,\n" +
            "CONCAT(u.first_name, ' ', u.last_name) AS affiliate_name, u.email, u.phone_number\n" +
            "FROM tbl_pay_affiliate AS pa LEFT JOIN tbl_users AS u ON pa.id_user = u.id WHERE (?1 IS NULL OR (SELECT concat(first_name, ' ',last_name, ' ', email) FROM tbl_users AS u WHERE u.id = pa.id_user) ILIKE %?1%);", nativeQuery = true)
    Page<Object[]> findByFilterData(String word, Pageable pageable);
}
