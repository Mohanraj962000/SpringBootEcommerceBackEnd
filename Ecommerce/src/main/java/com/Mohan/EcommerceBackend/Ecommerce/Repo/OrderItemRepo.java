package com.Mohan.EcommerceBackend.Ecommerce.Repo;

import com.Mohan.EcommerceBackend.Ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepo extends JpaRepository<OrderItem, UUID> {
}
