package com.ecov.multinivel.controller;

import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.generics.BuyDTO;
import com.ecov.multinivel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecov/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/get-products")
    public ResponseDTO GetProducts() {
        try{
            return productService._GetProducts();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-quotas-by-product")
    public ResponseDTO getQuotasByProduct(@RequestParam(name = "id-product") Long idProduct) {
        try{
            return productService._GetQuotasByProduct(idProduct);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PostMapping("/buy-product-by-integration")
    public ResponseDTO BuyProductByIntegration(@RequestBody BuyDTO buyDTO) {
        try{
            return productService._BuyProductByIntegration(buyDTO);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PostMapping("/buy-product-by-deposit")
    public ResponseDTO BuyProductByDeposit (@RequestBody BuyDTO buyDTO) {
        try{
            return productService._BuyProductByDeposit(buyDTO);
        } catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-articles-by-user")
    public ResponseDTO GetArticlesByUser(@RequestParam(name = "id-user") String idUser) {
        try{
            return productService._GetProductsByUser(idUser);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @GetMapping("/get-purchased-product-by-filter")
    public ResponseDTO GetArticlesBought(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String word) {
        try{
            return productService._GetArticlesPurchased(page, size, word);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
}
