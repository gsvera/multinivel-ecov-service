package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.PayAffiliateDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.generics.PageableResponseDTO;
import com.ecov.multinivel.entity.PayAffiliate;
import com.ecov.multinivel.entity.ProductXUser;
import com.ecov.multinivel.repository.PayAffiliateRepository;
import com.ecov.multinivel.repository.ProductXUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentAffiliateService {
    private final PayAffiliateRepository payAffiliateRepository;
    private final CommissionService commissionService;
    private final ProductXUserRepository productXUserRepository;
    private final ProductService productService;
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
    public ResponseDTO _ConfirmPayByProduct(Long idBuy, Long idPay) {
        Optional<ProductXUser> productXUser = productXUserRepository.findById(idBuy);
        if(productXUser.isPresent()) {
            productService._UpdateProductXUser(productXUser.get());
            return this._ConfirmPay(idPay);
        }
        return ResponseDTO.builder().error(true).message("No se encontrol el registro").build();
    }
    public ResponseDTO _ConfirmPay(Long idPay) {
        Optional<PayAffiliate> payAffiliate = payAffiliateRepository.findById(idPay);
        if(payAffiliate.isPresent()) {
            if(payAffiliate.get().getFirstPay()) {
                Optional<ProductXUser> productXUser = productXUserRepository.findByIdPayAffiliate(idPay);
                if(productXUser.isPresent()) {
                    productService._UpdateProductXUser(productXUser.get());
                    return this._UpdatePayAffiliate(idPay);
                }
            } else {
                return this._UpdatePayAffiliate(idPay);
            }
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
    }
    public ResponseDTO _UpdatePayAffiliate(Long idPay) {
        Optional<PayAffiliate> payAffiliate = payAffiliateRepository.findById(idPay);

        if(payAffiliate.isPresent()) {
            payAffiliate.orElseThrow().setStatusPay(1);
            payAffiliateRepository.save(payAffiliate.get());

            return commissionService._ChangeStatusByPay(payAffiliate.get().getId());
        }
        return ResponseDTO.builder().error(true).message("No se encontro el pago").build();
    }
}
