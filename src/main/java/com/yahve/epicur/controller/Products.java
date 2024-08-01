package com.yahve.epicur.controller;

import com.yahve.epicur.config.AppConstants;
import com.yahve.epicur.entity.Category;
import com.yahve.epicur.entity.Ingredient;
import com.yahve.epicur.entity.Product;

import com.yahve.epicur.service.CategoryService;
import com.yahve.epicur.service.IngredientService;
import com.yahve.epicur.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class Products {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IngredientService ingredientService;


    @GetMapping
    public List<Product> getProducts(
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortOrder
            ){
      return   productService.findAll(sortBy,sortOrder);
    }

    @PostMapping("/{categoryId}/products")
    public Product createProductInCategory(@PathVariable UUID categoryId, @RequestBody Product product) {
        Category category = categoryService.findById(categoryId);
        product.setCategory(category);
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteById(id);
    }

    @GetMapping("/keyword/{keyword}")
    public List<Product> getProductsByKeyword(@PathVariable String keyword){
        return productService.searchProductByKeyword(keyword);

    }

    @PutMapping("/update/productId/{id}")
    public Product updateProduct(@RequestBody Product product,@PathVariable UUID id){
      Product updatedProduct = productService.updateProduct(id,product);
        return updatedProduct;
    }

    @PostMapping("/{productId}/ingredients/{ingredientId}")
    public Product addIngredientToProduct(@PathVariable UUID productId, @PathVariable UUID ingredientId) {
        Ingredient ingredient = ingredientService.getIngredientById(ingredientId);
        return productService.addIngredientToProduct(productId, ingredient);
    }
}
