package com.ecov.multinivel.repository;

import com.ecov.multinivel.dto.CommissionAffiliateDTO;
import com.ecov.multinivel.entity.CommissionAffiliate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommissionAffiliateRepository extends JpaRepository<CommissionAffiliate, Long> {
    @Query(value = "SELECT new com.ecov.multinivel.dto.CommissionAffiliateDTO(ca.id, ca.idUserPay, ca.idUserCommission, ca.amountCommission, " +
            "ca.idPay, ca.statusPay, u, pa) " +
            "FROM CommissionAffiliate ca " +
            "LEFT JOIN ca.user u " +
            "LEFT JOIN ca.payAffiliate pa WHERE ca.statusPay IN (-1, 0) AND (?1 IS NULL OR u.firstName ILIKE %?1% OR u.lastName ILIKE %?1%) ORDER BY ca.id")
    Page<CommissionAffiliateDTO> findByFiltedData(String word, Pageable pageable);
    @Query(value = "SELECT * FROM tbl_commission_affiliate WHERE id_pay = ?1", nativeQuery = true)
    Optional<CommissionAffiliate> findByIdPay(Long id);
}
