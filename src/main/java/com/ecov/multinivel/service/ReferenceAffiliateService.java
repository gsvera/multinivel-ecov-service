package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.ConfigDTO;
import com.ecov.multinivel.dto.ReferenceAffiliateDTO;
import com.ecov.multinivel.entity.ReferenceAffiliate;
import com.ecov.multinivel.repository.ReferenceAffiliateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferenceAffiliateService {
    private final ReferenceAffiliateRepository referenceAffiliateRepository;
    private final ConfigService configService;
    public ReferenceAffiliateDTO _GetByReference(String codeReference) {
        Optional<ReferenceAffiliate> referenceAffiliate = referenceAffiliateRepository.findByCodeReference(codeReference);
        if(referenceAffiliate.isPresent()) {
            return new ReferenceAffiliateDTO(referenceAffiliate.get());
        }
        return null;
    }
    public void _SaveReferenceByUser(String code, String idUser) {
        ConfigDTO configDTO = configService._GetLastReference();
        if(configDTO != null) {
            String codeRefenceAffiliate = code + configDTO.valueNumber;
            ReferenceAffiliate referenceAffiliate = new ReferenceAffiliate(codeRefenceAffiliate, idUser);
            referenceAffiliateRepository.save(referenceAffiliate);
            configDTO.valueNumber = configDTO.valueNumber + 1;
            configService._UpdateConfig(configDTO);
        }
    }
}
