package com.Mohan.EcommerceBackend.Ecommerce.service;

import com.Mohan.EcommerceBackend.Ecommerce.Payload.CategoryDTO;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.CategoryResponse;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.CommonMapper;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.CategoryRepo;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.APIException;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.ResourceNotFoundException;
import com.Mohan.EcommerceBackend.Ecommerce.model.Category;
import com.Mohan.EcommerceBackend.Ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductService productService;


    public CategoryDTO createCategory(Category category) {
        Category savedCategory = categoryRepo.findByCategoryName(category.getCategoryName());

        if (savedCategory != null){
            throw new APIException("Category "+ category.getCategoryName()+" already exists");
        }

        savedCategory = categoryRepo.save(category);

        return CommonMapper.INSTANCE.toCategoryDTO(savedCategory);
    }

    public CategoryResponse getCategories(int pageNumber, int pageSize, String sortBy, String sortDir){

        //Sorting
        Sort sort = sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending():
                    Sort.by(sortBy).descending();

        //Pagination
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> pagedcategories = categoryRepo.findAll(pageDetails);

        List<Category> categories = pagedcategories.getContent();

        if(categories.isEmpty()){
            throw new APIException("Category is Empty");
        }

        //Converting Category to Category DTO
        List<CategoryDTO> categoryDTO = categories.stream().map(CommonMapper.INSTANCE::toCategoryDTO).toList();

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDTO);
        categoryResponse.setPageNumber(pagedcategories.getNumber());
        categoryResponse.setPageSize(pagedcategories.getSize());
        categoryResponse.setTotalElements(pagedcategories.getTotalElements());
        categoryResponse.setTotalPages(pagedcategories.getTotalPages());
        categoryResponse.setLastPage(pagedcategories.isLast());

        return categoryResponse;
    }

    public CategoryDTO updateCategory(Category category, UUID categoryId) {

        Category savedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        category.setCategoryId(categoryId);

        savedCategory = categoryRepo.save(category);

        return CommonMapper.INSTANCE.toCategoryDTO(savedCategory);

    }

    public String deleteCategory(UUID categoryId) {

        Category savedCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product> products = savedCategory.getProducts();

        for(Product product:products){
            productService.deleteProduct(product.getProductId());
        }

        categoryRepo.delete(savedCategory);

        return "Category Deleted with the Id:" + categoryId + "and Products";

    }
}
