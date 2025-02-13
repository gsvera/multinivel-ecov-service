package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.ReferenceAffiliate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReferenceAffiliateRepository extends JpaRepository<ReferenceAffiliate, Long> {
    Optional<ReferenceAffiliate> findByCodeReference(String codeReference);
}
