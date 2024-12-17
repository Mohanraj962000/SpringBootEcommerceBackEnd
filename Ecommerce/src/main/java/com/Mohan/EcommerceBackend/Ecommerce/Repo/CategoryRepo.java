package com.Mohan.EcommerceBackend.Ecommerce.Repo;

import com.Mohan.EcommerceBackend.Ecommerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {

    Category findByCategoryName(@NotBlank @Size(min = 3,message = "Category Name should have atleast 3 characters") String categoryName);
}
