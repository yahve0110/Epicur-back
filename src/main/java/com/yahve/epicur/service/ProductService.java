package com.yahve.epicur.service;


import com.yahve.epicur.entity.Ingredient;
import com.yahve.epicur.entity.Product;
import com.yahve.epicur.exception.ResourceNotFoundException;
import com.yahve.epicur.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public List<Product> findAll(String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return productRepository.findAll(sortByAndOrder);
    }

    public Product save(Product product) {
        product.setCreated(LocalDateTime.now());
        return productRepository.save(product);
    }
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }


    public List<Product> searchProductByKeyword(String keyword) {
      return   productRepository.findByNameLikeIgnoreCase('%' + keyword + '%');


    }

    public Product updateProduct(UUID id, Product product) {
        Product productFromDb = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productFromDb.setName(product.getName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setPrice(product.getPrice());
        return productRepository.save(productFromDb);
    }


    public Product addIngredientToProduct(UUID productId, Ingredient ingredient) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.getIngredients().add(ingredient);
        return productRepository.save(product);
    }



}
