package com.Mohan.EcommerceBackend.Ecommerce.service;

import com.Mohan.EcommerceBackend.Ecommerce.Payload.CartDTO;

import com.Mohan.EcommerceBackend.Ecommerce.Payload.CommonMapper;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.ProductDTO;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.CartItemRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.CartRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.ProductRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.UserRepo;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.APIException;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.ResourceNotFoundException;
import com.Mohan.EcommerceBackend.Ecommerce.model.Cart;
import com.Mohan.EcommerceBackend.Ecommerce.model.CartItem;
import com.Mohan.EcommerceBackend.Ecommerce.model.Product;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemRepo cartItemRepo;



    public CartDTO addProductToCart(String userEmail, UUID productId, int quantity) {
        User user = userRepo.findByEmail(userEmail);

        if(user == null){
            throw new ResourceNotFoundException("User","email",userEmail);
        }

        Cart cart = cartRepo.findByUserUserId(user.getUserId());

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));


        CartItem cartItem = cartItemRepo.findCartItemByProductIAndCartId(cart.getCartId(),productId);

        if(cartItem != null){
            throw new APIException("Product" + product.getProductName() + "already exists in the cart");
        }

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName() + "is not available");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please make an order" + product.getProductName() +
                    "less than or equal to the quantity"+ product.getQuantity());
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setSpecialPrice(product.getSpecialPrice());

        cartItemRepo.save(newCartItem);

//        product.setQuantity(product.getQuantity() - quantity);
        cart.setTotalPrice(cart.getTotalPrice() + (newCartItem.getSpecialPrice() * quantity));

        productRepo.save(product);
        cartRepo.save(cart);

        CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(cart);

        List<ProductDTO> productDTOS = cart.getCartItems().stream()
                .map(p -> CommonMapper.INSTANCE.toProductDTO(p.getProduct())).toList();


        cartDTO.setProducts(productDTOS);



        return cartDTO;
    }


    public List<CartDTO> getCarts() {
        List<Cart> carts = cartRepo.findAll();

        if (carts.isEmpty()){
            throw new APIException("No Cart Exists");
        }

        List<CartDTO> cartDTOS = carts.stream().map(cart -> {
           CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(cart);

           List<ProductDTO> productDTOS = cart.getCartItems().stream().map(
                   prod -> CommonMapper.INSTANCE.toProductDTO(prod.getProduct())
           ).toList();

           cartDTO.setProducts(productDTOS);

           return cartDTO;
        }).toList();

        return cartDTOS;

    }

    public CartDTO getCartById(String emailId) {
        User user = userRepo.findByEmail(emailId);

        if(user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Cart cart = cartRepo.findCartByEmailandCartId(emailId,user.getCart().getCartId());

        CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(cart);

        List<ProductDTO> productDTOS = cart.getCartItems().stream().map(
                prod->CommonMapper.INSTANCE.toProductDTO(prod.getProduct())
        ).toList();

        cartDTO.setProducts(productDTOS);

        return cartDTO;

    }


    public CartDTO updateProductQuantity(String emailId, UUID productId, int quantity) {
        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Cart cart = cartRepo.findById(user.getCart().getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart","cartId",user.getCart().getCartId()));

        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        if(product.getQuantity() == 0){
            throw new APIException(product.getProductName()+" is not available");
        }

        if(product.getQuantity() < quantity){
            throw new APIException("Please make an order" + product.getProductName() +
                    "less than or equal to the quantity"+ product.getQuantity());
        }

        CartItem cartItem = cartItemRepo.findCartItemByProductIAndCartId(cart.getCartId(),productId);

        if (cartItem == null){
            throw new APIException("Product" + product.getProductName() + "not available in the cart");
        }

        int cartItemQuantity = cartItem.getQuantity() + quantity;

        if (product.getQuantity() <= cartItemQuantity){
            throw new APIException("You have Reached your Limit");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getSpecialPrice() * cartItem.getQuantity());

        cartItem.setSpecialPrice(product.getSpecialPrice());
        cartItem.setQuantity(cartItemQuantity);
        cartItem.setDiscount(product.getDiscount());

        cart.setTotalPrice(cartPrice + (cartItem.getSpecialPrice() * cartItemQuantity));
        cartItem = cartItemRepo.save(cartItem);

//        product.setQuantity(product.getQuantity() - quantity);
        productRepo.save(product);
        CartDTO cartDTO = CommonMapper.INSTANCE.toCartDTO(cart);

        List<ProductDTO> productDTOS = cart.getCartItems().stream().map(
                prod->CommonMapper.INSTANCE.toProductDTO(prod.getProduct())
        ).toList();

        cartDTO.setProducts(productDTOS);

        return cartDTO;


    }

    public String deleteProductFromCart(String emailId, UUID productId) {
        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Cart cart = cartRepo.findCartByEmailandCartId(emailId,user.getCart().getCartId());

        CartItem cartItem = cartItemRepo.findCartItemByProductIAndCartId(cart.getCartId(),productId);

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getSpecialPrice() * cartItem.getQuantity()));

        Product product = cartItem.getProduct();
//        product.setQuantity(product.getQuantity() + cartItem.getQuantity());

        cartItemRepo.deleteCartItemByProductIdAndCartId(cart.getCartId(),productId);
        System.out.println("Test");
        return "Product" + cartItem.getProduct().getProductName() + " deleted successfully";

    }

    public String deleteProductFromCartUsingCartId(UUID cartId, UUID productId) {


        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart","cartId",cartId));

        CartItem cartItem = cartItemRepo.findCartItemByProductIAndCartId(cartId,productId);

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getSpecialPrice() * cartItem.getQuantity()));

        Product product = cartItem.getProduct();
//        product.setQuantity(product.getQuantity() + cartItem.getQuantity());

        cartItemRepo.deleteCartItemByProductIdAndCartId(cart.getCartId(),productId);

        return "Product" + cartItem.getProduct().getProductName() + " deleted successfully";

    }
}


