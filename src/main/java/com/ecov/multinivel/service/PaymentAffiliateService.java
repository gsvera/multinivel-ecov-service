package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.PayAffiliateDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.generics.PageableResponseDTO;
import com.ecov.multinivel.repository.PayAffiliateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentAffiliateService {
    private final PayAffiliateRepository payAffiliateRepository;
    public ResponseDTO _GetByFilterData(int page, int size, String word) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Object[]> listPays = payAffiliateRepository.findByFilterData(word, pageRequest);
        List<PayAffiliateDTO> listPayDto = new ArrayList<>();

        for(Object[] row : listPays) {
            PayAffiliateDTO payAffiliateDTO = new PayAffiliateDTO();
            payAffiliateDTO.id = (Long)row[0];
            payAffiliateDTO.amount = (double) row[1];
            payAffiliateDTO.createdDate = (Timestamp) row[2];
            payAffiliateDTO.description = (String) row[3];
            payAffiliateDTO.statusPay = (int) row[4];
            payAffiliateDTO.paymentFile = (String) row[5];
            payAffiliateDTO.payMethod = (String) row[6];
            payAffiliateDTO.nameAffiliate = (String)row[7];
            payAffiliateDTO.emailAffiliate = (String) row[8];
            payAffiliateDTO.phoneNumber = (String) row[9];
            listPayDto.add(payAffiliateDTO);
        }
        return ResponseDTO.builder().items(
                    PageableResponseDTO
                        .builder()
                        .isLastPage(listPays.isLast())
                        .result(listPayDto)
                        .build()
                ).build();
    }
}
