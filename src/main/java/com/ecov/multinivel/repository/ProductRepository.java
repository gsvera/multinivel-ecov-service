package com.ecov.multinivel.repository;

import com.ecov.multinivel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
