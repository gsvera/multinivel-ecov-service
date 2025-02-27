package com.ecov.multinivel.controller;

import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.service.PaymentAffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return ResponseDTO.builder().error(true).message(ex.getMessage()).build();
        }
    }
}
