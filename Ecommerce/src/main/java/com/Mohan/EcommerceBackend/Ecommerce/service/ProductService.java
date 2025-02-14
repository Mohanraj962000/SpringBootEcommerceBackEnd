package com.Mohan.EcommerceBackend.Ecommerce.service;


import com.Mohan.EcommerceBackend.Ecommerce.Payload.CommonMapper;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.ProductDTO;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.ProductResponse;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.CartRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.CategoryRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.ProductRepo;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.APIException;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.ResourceNotFoundException;
import com.Mohan.EcommerceBackend.Ecommerce.model.Cart;
import com.Mohan.EcommerceBackend.Ecommerce.model.Category;
import com.Mohan.EcommerceBackend.Ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private FileService fileService;

    @Value("${product.image}")
    private String path;

    @Autowired
    private CategoryRepo categoryRepo;

    public ProductDTO addProduct(UUID categoryId, Product product) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        boolean isNewProduct = true;

        List<Product> products = category.getProducts();

        for(int i=0;i<products.size();i++){
            if(products.get(i).getProductName().equals(product.getProductName())
                && products.get(i).getDescription().equals(product.getDescription())){
                isNewProduct = false;
                break;
            }
        }

        if(isNewProduct){
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);

            Product saveProduct = repo.save(product);

            return CommonMapper.INSTANCE.toProductDTO(saveProduct);
        }

        else {
            throw new APIException("Product already exists");
        }

    }

    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir){

        //Sorting
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
                    Sort.by(sortBy).descending();

        //Pagination
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> pagedProducts = repo.findAll(pageDetails);

        //Get Only Products from Paged Products
        List<Product> products = pagedProducts.getContent();

        //Converting to Product DTO
        List<ProductDTO> productDTO = products.stream().map(CommonMapper.INSTANCE::toProductDTO).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pagedProducts.getNumber());
        productResponse.setPageSize(pagedProducts.getSize());
        productResponse.setTotalElements(pagedProducts.getTotalElements());
        productResponse.setTotalPages(pagedProducts.getTotalPages());
        productResponse.setLastPage(pagedProducts.isLast());

        return productResponse;
    }


    public ProductDTO updateProduct(UUID productId, Product product) {
        Product savedProduct = repo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        product.setImage(savedProduct.getImage());
        product.setProductId(productId);
        product.setCategory(savedProduct.getCategory());

        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product finalProduct = repo.save(product);

        return CommonMapper.INSTANCE.toProductDTO(finalProduct);


    }



    public ProductDTO updateProductImage(UUID productId, MultipartFile image) throws IOException {
        Product product = repo.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        String filename = fileService.uploadImage(path,image);

        product.setImage(filename);

        Product updatedProduct = repo.save(product);

        return CommonMapper.INSTANCE.toProductDTO(updatedProduct);
    }

    public String deleteProduct(UUID productId) {

        Product product = repo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        List<Cart> cart = cartRepo.findCartsByProductId(productId);

        cart.forEach(cart1 -> {
            cartService.deleteProductFromCartUsingCartId(cart1.getCartId(),productId);
        });

        repo.delete(product);

        return "Product Deleted Successfully";

    }

    public ProductResponse getProductsByCategory(UUID categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> pagedProducts = repo.findByCategoryCategoryId(category.getCategoryId(),pageable);

        List<Product> products = pagedProducts.getContent();

        List<ProductDTO> productDTO = products.stream().map(CommonMapper.INSTANCE::toProductDTO).toList();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pagedProducts.getNumber());
        productResponse.setPageSize(pagedProducts.getSize());
        productResponse.setTotalElements(pagedProducts.getTotalElements());
        productResponse.setTotalPages(pagedProducts.getTotalPages());
        productResponse.setLastPage(pagedProducts.isLast());

        return productResponse;





    }

    public ProductResponse searchByKeyword(String keyword, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> pagedProducts = repo.findByProductNameContainingOrDescriptionContaining(keyword,keyword,pageable);

        List<Product> products = pagedProducts.getContent();

        List<ProductDTO> productDTO = products.stream().map(CommonMapper.INSTANCE::toProductDTO).toList();

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pagedProducts.getNumber());
        productResponse.setPageSize(pagedProducts.getSize());
        productResponse.setTotalElements(pagedProducts.getTotalElements());
        productResponse.setTotalPages(pagedProducts.getTotalPages());
        productResponse.setLastPage(pagedProducts.isLast());

        return productResponse;

    }
}
