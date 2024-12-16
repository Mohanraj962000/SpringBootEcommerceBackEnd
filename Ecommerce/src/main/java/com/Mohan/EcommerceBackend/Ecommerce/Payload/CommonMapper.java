package com.Mohan.EcommerceBackend.Ecommerce.Payload;

import com.Mohan.EcommerceBackend.Ecommerce.model.Role;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommonMapper {

    CommonMapper INSTANCE = Mappers.getMapper(CommonMapper.class);


    UserDTO toUserDTO(User user);
    User toUserEntity(UserDTO userDTO);

    RoleDTO toRoleDTO(Role role);
    Role toRoleEntity(RoleDTO roleDTO);
}
