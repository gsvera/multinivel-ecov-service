package com.ecov.multinivel.controller;

import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.service.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ecov/affiliate")
public class AffiliateController {
    @Autowired
    private AffiliateService affiliateService;
    @GetMapping("/get-affiliate-tree")
    public ResponseDTO GetAffiliateTree() {
        try{
           return  affiliateService._GetAllAffiliateTree();

        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-data-affiliate")
    public ResponseDTO GetDataAffiliate(){
        try{
            return affiliateService._GetDataAffiliate();
        } catch (Exception ex) {
            return ResponseDTO.builder().error(true).message(ex.getMessage()).build();
        }
    }
}
