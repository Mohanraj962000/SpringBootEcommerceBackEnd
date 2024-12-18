package com.Mohan.EcommerceBackend.Ecommerce.Payload;

import com.Mohan.EcommerceBackend.Ecommerce.model.Address;
import com.Mohan.EcommerceBackend.Ecommerce.model.Cart;
import com.Mohan.EcommerceBackend.Ecommerce.model.Category;
import com.Mohan.EcommerceBackend.Ecommerce.model.Order;
import com.Mohan.EcommerceBackend.Ecommerce.model.OrderItem;
import com.Mohan.EcommerceBackend.Ecommerce.model.Product;
import com.Mohan.EcommerceBackend.Ecommerce.model.Role;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T20:16:18+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
public class CommonMapperImpl implements CommonMapper {

    @Override
    public ProductDTO toProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId( product.getProductId() );
        productDTO.setProductName( product.getProductName() );
        productDTO.setDescription( product.getDescription() );
        productDTO.setImage( product.getImage() );
        productDTO.setQuantity( product.getQuantity() );
        productDTO.setPrice( product.getPrice() );
        productDTO.setDiscount( product.getDiscount() );
        productDTO.setSpecialPrice( product.getSpecialPrice() );

        return productDTO;
    }

    @Override
    public Product toProductEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( productDTO.getProductId() );
        product.setProductName( productDTO.getProductName() );
        product.setImage( productDTO.getImage() );
        product.setDescription( productDTO.getDescription() );
        product.setQuantity( productDTO.getQuantity() );
        product.setPrice( productDTO.getPrice() );
        product.setDiscount( productDTO.getDiscount() );
        product.setSpecialPrice( productDTO.getSpecialPrice() );

        return product;
    }

    @Override
    public CategoryDTO toCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCategoryId( category.getCategoryId() );
        categoryDTO.setCategoryName( category.getCategoryName() );

        return categoryDTO;
    }

    @Override
    public Category toCategoryEntity(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setCategoryId( categoryDTO.getCategoryId() );
        category.setCategoryName( categoryDTO.getCategoryName() );

        return category;
    }

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
    public CartDTO toCartDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartId( cart.getCartId() );
        cartDTO.setTotalPrice( cart.getTotalPrice() );

        return cartDTO;
    }

    @Override
    public Cart toCartEntity(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setCartId( cartDTO.getCartId() );
        cart.setTotalPrice( cartDTO.getTotalPrice() );

        return cart;
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

    @Override
    public AddressDTO toAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId( address.getAddressId() );
        addressDTO.setBuildingName( address.getBuildingName() );
        addressDTO.setCity( address.getCity() );
        addressDTO.setCountry( address.getCountry() );
        addressDTO.setPincode( address.getPincode() );
        addressDTO.setState( address.getState() );
        addressDTO.setStreet( address.getStreet() );

        return addressDTO;
    }

    @Override
    public Address toAddressEntity(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setAddressId( addressDTO.getAddressId() );
        address.setBuildingName( addressDTO.getBuildingName() );
        address.setCity( addressDTO.getCity() );
        address.setCountry( addressDTO.getCountry() );
        address.setPincode( addressDTO.getPincode() );
        address.setState( addressDTO.getState() );
        address.setStreet( addressDTO.getStreet() );

        return address;
    }

    @Override
    public OrderDTO toOrderDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId( order.getOrderId() );
        orderDTO.setEmail( order.getEmail() );
        orderDTO.setOrderItems( orderItemListToOrderItemDTOList( order.getOrderItems() ) );
        orderDTO.setOrderDate( order.getOrderDate() );
        orderDTO.setTotalAmount( order.getTotalAmount() );
        orderDTO.setOrderStatus( order.getOrderStatus() );

        return orderDTO;
    }

    @Override
    public Order toOrderEntity(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        order.setOrderId( orderDTO.getOrderId() );
        order.setEmail( orderDTO.getEmail() );
        order.setOrderItems( orderItemDTOListToOrderItemList( orderDTO.getOrderItems() ) );
        order.setOrderDate( orderDTO.getOrderDate() );
        order.setTotalAmount( orderDTO.getTotalAmount() );
        order.setOrderStatus( orderDTO.getOrderStatus() );

        return order;
    }

    @Override
    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrderItemId( orderItem.getOrderItemId() );
        orderItemDTO.setProduct( toProductDTO( orderItem.getProduct() ) );
        orderItemDTO.setQuantity( orderItem.getQuantity() );
        orderItemDTO.setDiscount( orderItem.getDiscount() );

        return orderItemDTO;
    }

    @Override
    public OrderItem toOrderItemEntity(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setOrder( order );

        return orderItem;
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

    protected List<OrderItemDTO> orderItemListToOrderItemDTOList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemDTO> list1 = new ArrayList<OrderItemDTO>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( toOrderItemDTO( orderItem ) );
        }

        return list1;
    }

    protected OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO) {
        if ( orderItemDTO == null ) {
            return null;
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId( orderItemDTO.getOrderItemId() );
        orderItem.setProduct( toProductEntity( orderItemDTO.getProduct() ) );
        orderItem.setQuantity( orderItemDTO.getQuantity() );
        orderItem.setDiscount( orderItemDTO.getDiscount() );

        return orderItem;
    }

    protected List<OrderItem> orderItemDTOListToOrderItemList(List<OrderItemDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItem> list1 = new ArrayList<OrderItem>( list.size() );
        for ( OrderItemDTO orderItemDTO : list ) {
            list1.add( orderItemDTOToOrderItem( orderItemDTO ) );
        }

        return list1;
    }
}
