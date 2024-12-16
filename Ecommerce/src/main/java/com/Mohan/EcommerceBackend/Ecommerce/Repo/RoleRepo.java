package com.Mohan.EcommerceBackend.Ecommerce.Repo;

import com.Mohan.EcommerceBackend.Ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepo extends JpaRepository<Role, UUID> {

    Role findByRoleName(String string);
}