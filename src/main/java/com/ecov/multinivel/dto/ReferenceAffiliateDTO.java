package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.ReferenceAffiliate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenceAffiliateDTO {
    public Long id;
    public String codeReference;
    public String idUser;
    public ReferenceAffiliateDTO(ReferenceAffiliate referenceAffiliate) {
        this.id = referenceAffiliate.getId();
        this.codeReference = referenceAffiliate.getCodeReference();
        this.idUser = referenceAffiliate.getIdUser();
    }
}
