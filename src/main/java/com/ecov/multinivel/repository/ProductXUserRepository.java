package com.ecov.multinivel.repository;

import com.ecov.multinivel.dto.ProductXUserDTO;
import com.ecov.multinivel.entity.ProductXUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductXUserRepository extends JpaRepository<ProductXUser, Long> {
    @Query(value = "SELECT pu.id, p.name, pu.date_buy, pu.payment_file, pu.pay_method,\n" +
            "(SELECT name_status FROM tbl_status_buy AS sb WHERE sb.id = pu.id_status_buy )\n" +
            "FROM tbl_product_x_user AS pu LEFT JOIN tbl_product p ON pu.id_product = p.id WHERE pu.id_user = ?1;", nativeQuery = true)
    List<Object[]> findProductByUser(String idUser);
    @Query(value = "SELECT pu.id, p.name, pu.date_buy, pu.payment_file, pu.pay_method, \n" +
            "(SELECT name_status FROM tbl_status_buy AS sb WHERE sb.id = pu.id_status_buy),\n" +
            "(SELECT concat(first_name, ' ', last_name) FROM tbl_users AS u WHERE u.id = pu.id_user),\n"+
            "(SELECT status_pay FROM tbl_pay_affiliate AS pa WHERE pu.id_pay_affiliate = pa.id)\n" +
            "FROM tbl_product_x_user AS pu LEFT JOIN tbl_product AS p ON pu.id_product = p.id WHERE (?1 IS NULL OR (SELECT concat(first_name, ' ',last_name) FROM tbl_users AS u WHERE u.id = pu.id_user) ILIKE %?1%)", nativeQuery = true)
    Page<Object[]> findProductByFilter(String word, Pageable pageable);
}
