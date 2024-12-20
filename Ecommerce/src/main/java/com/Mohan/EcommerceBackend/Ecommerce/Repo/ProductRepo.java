package com.Mohan.EcommerceBackend.Ecommerce.Repo;

import com.Mohan.EcommerceBackend.Ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {
    Page<Product> findByCategoryCategoryId(UUID categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingOrDescriptionContaining(String productName, String description, Pageable pageable);
}
