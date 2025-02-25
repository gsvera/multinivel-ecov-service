package com.ecov.multinivel.service;

import com.ecov.multinivel.config.ConstantsConfig;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.entity.CommissionAffiliate;
import com.ecov.multinivel.entity.User;
import com.ecov.multinivel.repository.CommissionAffiliateRepository;
import com.ecov.multinivel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
