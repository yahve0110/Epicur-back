package com.yahve.epicur.repository;

import com.yahve.epicur.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByNameLikeIgnoreCase(String keyword);
}
