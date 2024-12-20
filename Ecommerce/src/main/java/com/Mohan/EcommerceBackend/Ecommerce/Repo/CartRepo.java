package com.Mohan.EcommerceBackend.Ecommerce.Repo;

import com.Mohan.EcommerceBackend.Ecommerce.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartRepo extends JpaRepository<Cart, UUID> {

    Cart findByUserUserId(UUID userId);

    @Query("SELECT c FROM Cart c WHERE c.user.email = ?1 and c.id = ?2 ")
    Cart findCartByEmailandCartId(String email, UUID cartId);

    @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Cart> findCartsByProductId(UUID productId);
}
