package com.Mohan.EcommerceBackend.Ecommerce.Payload;

import com.Mohan.EcommerceBackend.Ecommerce.model.*;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommonMapper {

    CommonMapper INSTANCE = Mappers.getMapper(CommonMapper.class);

    CategoryDTO toCategoryDTO(Category category);
    Category toCategoryEntity(CategoryDTO categoryDTO);

    UserDTO toUserDTO(User user);
    User toUserEntity(UserDTO userDTO);

    RoleDTO toRoleDTO(Role role);
    Role toRoleEntity(RoleDTO roleDTO);


    CartDTO toCartDTO(Cart cart);
    Cart toCartEntity(CartDTO cartDTO);

    ProductDTO toProductDTO(Product product);
    Product toProductEntity(ProductDTO productDTO);

}
