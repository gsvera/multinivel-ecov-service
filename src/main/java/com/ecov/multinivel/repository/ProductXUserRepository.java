package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.ProductXUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductXUserRepository extends JpaRepository<ProductXUser, Long> {
    @Query(value = "SELECT pu.id, p.name, pu.date_buy, pu.payment_file, pu.pay_method,\n" +
            "(SELECT name_status FROM ecov.tbl_status_buy as sb WHERE sb.id = pu.id_status_buy )\n" +
            "FROM ecov.tbl_product_x_user as pu LEFT JOIN ecov.tbl_product p ON pu.id_product = p.id WHERE pu.id_user = ?1  ;", nativeQuery = true)
    List<Object[]> findProductByUser(String idUser);
}
