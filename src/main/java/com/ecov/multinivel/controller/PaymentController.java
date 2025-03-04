package com.ecov.multinivel.controller;

import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.service.PaymentAffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ecov/payment")
public class PaymentController {
    @Autowired
    private PaymentAffiliateService paymentAffiliateService;

    @GetMapping("/get-by-filter-data")
    public ResponseDTO GetByFilterData(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String word) {
        try{
            return paymentAffiliateService._GetByFilterData(page,size, word);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PutMapping("/confirmed-buy-by-pay")
    public ResponseDTO ConfirmedBuy(@RequestParam(name = "id-buy", required = false)Long idBuy, @RequestParam(name = "id-pay")Long idPay) {
        try {
            if(idBuy != null) {
                return paymentAffiliateService._ConfirmPayByProduct(idBuy, idPay);
            } else {
                return paymentAffiliateService._ConfirmPay(idPay);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PutMapping("/reject-pay")
    public ResponseDTO RejectPay(@RequestBody Map<String, Object> data){
        try{
            Long idPay = ((Number) data.get("idPay")).longValue();
            String observation = (String) data.get("observation");
            return paymentAffiliateService._RejectPay(idPay, observation);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message(ex.getMessage()).build();
        }
    }
}
