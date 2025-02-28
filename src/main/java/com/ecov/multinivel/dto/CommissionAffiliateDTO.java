package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.CommissionAffiliate;
import com.ecov.multinivel.entity.PayAffiliate;
import com.ecov.multinivel.entity.User;

public class CommissionAffiliateDTO {
    public Long id;
    public String idUserPay;
    public String idUserCommission;
    public double amountCommission;
    public Long idPay;
    public int statusPay;
    public UserDTO userDTO;
    public PayAffiliateDTO payAffiliateDTO;

    public CommissionAffiliateDTO(Long id, String idUserPay, String idUserCommission, double amountCommission,
                                  Long idPay, int statusPay, User user, PayAffiliate payAffiliate) {
        this.id = id;
        this.idUserPay = idUserPay;
        this.idUserCommission = idUserCommission;
        this.amountCommission = amountCommission;
        this.idPay = idPay;
        this.statusPay = statusPay;

        if (user != null) {
            UserDTO userDto = new UserDTO();
            userDto.id = user.getId();
            userDto.firstName = user.getFirstName();
            userDto.lastName = user.getLastName();
            this.userDTO = userDto;
        }

        if (payAffiliate != null) {
            PayAffiliateDTO payAffiliateDTO = new PayAffiliateDTO();
            payAffiliateDTO.id = payAffiliate.getId();
            payAffiliateDTO.amount = payAffiliate.getAmount();
            payAffiliateDTO.createdDate = payAffiliate.getCreatedDate();
            payAffiliateDTO.description = payAffiliate.getDescription();
            payAffiliateDTO.statusPay = payAffiliate.getStatusPay();
            this.payAffiliateDTO = payAffiliateDTO;
        }
    }
}
