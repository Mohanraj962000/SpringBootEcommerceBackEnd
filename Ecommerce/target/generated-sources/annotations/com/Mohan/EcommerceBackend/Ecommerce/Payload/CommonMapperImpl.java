package com.Mohan.EcommerceBackend.Ecommerce.Payload;

import com.Mohan.EcommerceBackend.Ecommerce.model.Role;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-12T19:19:14+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class CommonMapperImpl implements CommonMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId( user.getUserId() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        userDTO.setMobileNumber( user.getMobileNumber() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setRoles( roleSetToRoleDTOSet( user.getRoles() ) );

        return userDTO;
    }

    @Override
    public User toUserEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userDTO.getUserId() );
        user.setFirstName( userDTO.getFirstName() );
        user.setLastName( userDTO.getLastName() );
        user.setMobileNumber( userDTO.getMobileNumber() );
        user.setEmail( userDTO.getEmail() );
        user.setPassword( userDTO.getPassword() );
        user.setRoles( roleDTOSetToRoleSet( userDTO.getRoles() ) );

        return user;
    }

    @Override
    public RoleDTO toRoleDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setRoleName( role.getRoleName() );

        return roleDTO;
    }

    @Override
    public Role toRoleEntity(RoleDTO roleDTO) {
        if ( roleDTO == null ) {
            return null;
        }

        Role role = new Role();

        role.setRoleName( roleDTO.getRoleName() );

        return role;
    }

    protected Set<RoleDTO> roleSetToRoleDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleDTO> set1 = new LinkedHashSet<RoleDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( toRoleDTO( role ) );
        }

        return set1;
    }

    protected Set<Role> roleDTOSetToRoleSet(Set<RoleDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleDTO roleDTO : set ) {
            set1.add( toRoleEntity( roleDTO ) );
        }

        return set1;
    }
}
