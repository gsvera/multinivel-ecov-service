package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.DetailPayProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailPayProductRepository extends JpaRepository<DetailPayProduct, Long> {
    List<DetailPayProduct> findByIdProduct(Long idProduct);
}
