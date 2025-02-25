package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.*;
import com.ecov.multinivel.dto.generics.BuyDTO;
import com.ecov.multinivel.entity.*;
import com.ecov.multinivel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DetailPayProductRepository detailPayProductRepository;
    private final ProductXUserRepository productXUserRepository;
    private final PayAffiliateRepository payAffiliateRepository;
    private final StatusBuyRepository statusBuyRepository;
    private final CommissionService commissionService;
    public ResponseDTO _GetProducts() {
        List<Product> products = productRepository.findAll();

        return ResponseDTO.builder().items(
                products.stream().map(item -> new ProductDTO(item)).collect(Collectors.toList())
        ).build();
    }
    public ResponseDTO _GetProductsByUser(String idUser) {
        List<Object[]> listProduct =  productXUserRepository.findProductByUser(idUser);
        List<ProductXUserDTO> listProductUser = new ArrayList<>();
        for(Object[] row : listProduct) {
            ProductXUserDTO productXUserDTO = new ProductXUserDTO();
            productXUserDTO.id = (Long)row[0];
            productXUserDTO.nameProduct = (String)row[1];
            productXUserDTO.dateBuy = (Timestamp) row[2];
            productXUserDTO.paymentFile = (String) row[3];
            productXUserDTO.payMethod = (String) row[4];
            productXUserDTO.statusBuy = (String) row[5];
            listProductUser.add(productXUserDTO);
        }
        return ResponseDTO.builder().items(listProductUser).build();
    }
    public ResponseDTO _GetQuotasByProduct(Long id) {
        List<DetailPayProduct> detailPayProductList = detailPayProductRepository.findByIdProduct(id);
        return ResponseDTO.builder().items(
                detailPayProductList.stream().map(item -> new DetailPayProductDTO(item)).collect(Collectors.toList())
        ).build();
    }
    public ResponseDTO _BuyProductByIntegration(BuyDTO buyDTO) {
        StatusBuy statusBuy = statusBuyRepository.findByOrderStatus(2); // Se pone el estatus de comprado este para integraciones de pagos
        return this._GeneratePay(buyDTO, statusBuy, true);
    }
    public ResponseDTO _BuyProductByDeposit(BuyDTO buyDTO) {
        StatusBuy statusBuy = statusBuyRepository.findByOrderStatus(1); // Se pone el estatus validacion de pago
        return this._GeneratePay(buyDTO, statusBuy, false);
    }
    public ResponseDTO _GeneratePay(BuyDTO buyDTO, StatusBuy statusBuy, Boolean payCommission) {
        Optional<User> user = userRepository.findById(buyDTO.idUser);
        Optional<Product> product = productRepository.findById(buyDTO.idProduct);

        if(!user.isPresent() || !product.isPresent()) {
            return ResponseDTO.builder().error(true).message("Hubo un problema intentelo mas tarde").build();
        }

        ProductXUser productXUser = new ProductXUser();
        productXUser.setIdProduct(buyDTO.idProduct);
        productXUser.setIdUser(buyDTO.idUser);
        productXUser.setIdStatusBuy(statusBuy.getId());
        productXUser.setDateBuy(Timestamp.from(Instant.now()));
        productXUser.setPaymentFile(buyDTO.paymentFile);
        productXUser.setPayMethod(buyDTO.payMethod);
        productXUserRepository.save(productXUser);

        PayAffiliate payAffiliate = new PayAffiliate();
        payAffiliate.setIdUser(user.get().getId());
        payAffiliate.setAmount(buyDTO.amountToCharge);
        payAffiliate.setDescription("Sistema vendido a :" + user.get().getFirstName() + " " + user.get().getLastName());
        payAffiliate.setStatusPay(buyDTO.statusPay);
        payAffiliate.setCreatedDate(Timestamp.from(Instant.now()));
        payAffiliate.setPaymentFile(buyDTO.paymentFile);
        payAffiliate.setPayMethod(buyDTO.payMethod);
        payAffiliateRepository.save(payAffiliate);

        commissionService._GenerateCommissionByBuy(new UserDTO(user.get()), payAffiliate.getId(), product.get().getFirstCommission(),payCommission);

        return ResponseDTO.builder().message("Se creo el registro con éxito").build();
    }
}
