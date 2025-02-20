package com.ecov.multinivel.controller;

import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.service.AffiliateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/get-affiliate-tree/by-user")
    public ResponseDTO GetAffiliateTreeByUser(@RequestParam(name = "id-user") String idUser) {
        try{
            return  affiliateService._GetAffiliateByUser(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-data-affiliate")
    public ResponseDTO GetDataAffiliate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String word){
        try{
            return affiliateService._GetDataAffiliate(page, size, word);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
