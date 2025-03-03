package com.ecov.multinivel.service;

import com.ecov.multinivel.config.ConstantsConfig;
import com.ecov.multinivel.dto.CommissionAffiliateDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.dto.generics.PageableResponseDTO;
import com.ecov.multinivel.entity.CommissionAffiliate;
import com.ecov.multinivel.entity.User;
import com.ecov.multinivel.repository.CommissionAffiliateRepository;
import com.ecov.multinivel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommissionService {
    private final ConstantsConfig constantsConfig;
    private final UserRepository userRepository;
    private final CommissionAffiliateRepository commissionAffiliateRepository;

    public ResponseDTO  _GenerateCommissionByBuy(UserDTO userDTO, Long idPay, double amountCommission, Boolean readyToPay) {
        int statusPay = readyToPay == true ? 0 : -1;
        Optional<User> userParent = userRepository.findById(userDTO.getReferenceParent());

        if(userParent.get().getWorkgroupId() != constantsConfig.getWorkGroupIdAdmin() && idPay != 0) {
            CommissionAffiliate commissionAffiliate = new CommissionAffiliate();
            commissionAffiliate.setIdUserPay(userDTO.getId());
            commissionAffiliate.setIdUserCommission(userParent.get().getId());
            commissionAffiliate.setAmountCommission(amountCommission);
            commissionAffiliate.setStatusPay(statusPay);
            commissionAffiliate.setIdPay(idPay);

            commissionAffiliateRepository.save(commissionAffiliate);
            return ResponseDTO.builder().message("Se genero la comision con exito").build();
        }
        return ResponseDTO.builder().error(true).message("No se encontro afiliado de referencia").build();
    }
    public ResponseDTO _GetComissionByFilterData(int page, int size, String word) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommissionAffiliateDTO> list = commissionAffiliateRepository.findByFiltedData(word, pageRequest);

        return ResponseDTO.builder().items(
                PageableResponseDTO
                        .builder()
                        .isLastPage(list.isLast())
                        .result(list.stream().collect(Collectors.toList()))
                        .build())
                .build();
    }
    public ResponseDTO _ChangeStatusByPay(Long idPay) {
        Optional<CommissionAffiliate> commissionAffiliate = commissionAffiliateRepository.findByIdPay(idPay);
        if(commissionAffiliate.isPresent()) {
            commissionAffiliate.orElseThrow().setStatusPay(0);
            commissionAffiliateRepository.save(commissionAffiliate.get());
            return ResponseDTO.builder().message("Cambio de estatus de comision").build();
        }
        return ResponseDTO.builder().build();
    }
}
