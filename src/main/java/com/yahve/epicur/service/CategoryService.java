package com.yahve.epicur.service;

import com.yahve.epicur.entity.Category;
import com.yahve.epicur.entity.Product;
import com.yahve.epicur.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories(String sortBy, String sortOrder, boolean veganOnly, boolean onSiteOnly, Double priceFrom, Double priceTo, List<String> ingredients) {
        List<Category> categories = categoryRepository.findAll();

        categories.forEach(category -> {
            List<Product> filteredProducts = category.getProducts().stream()
                    .filter(product -> filterProduct(product, veganOnly, onSiteOnly, priceFrom, priceTo, ingredients))
                    .collect(Collectors.toList());

            // Apply sorting after filtering
            List<Product> sortedProducts = filteredProducts.stream()
                    .sorted(getComparator(sortBy, sortOrder))
                    .collect(Collectors.toList());

            // Debug print for sorted products
            sortedProducts.forEach(product -> System.out.println("Product: " + product.getName() + ", Rating: " + product.getRating()));

            // Set the sorted products back to the category
            category.setProducts(sortedProducts);
        });

        return categories;
    }

    private boolean filterProduct(Product product, boolean veganOnly, boolean onSiteOnly, Double priceFrom, Double priceTo, List<String> ingredients) {
        boolean matchesIngredients = ingredients == null || ingredients.isEmpty() || product.hasIngredients(ingredients);
        return (!veganOnly || product.isVegan())
                && (!onSiteOnly || product.isOnSiteOnly())
                && (priceFrom == null || product.getPrice() >= priceFrom)
                && (priceTo == null || product.getPrice() <= priceTo)
                && matchesIngredients;
    }

    private Comparator<Product> getComparator(String sortBy, String sortOrder) {
        Comparator<Product> comparator;

        // Primary sorting by the specified field
        switch (sortBy.toLowerCase()) {
            case "price":
                comparator = Comparator.comparingDouble(Product::getPrice);
                break;
            case "rating":
                comparator = Comparator.comparingDouble(Product::getRating);
                break;
            default:
                comparator = Comparator.comparing(Product::getCreated).reversed();  // Default sorting by creation date descending
                break;
        }

        // Adjust sorting order
        if ("desc".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    public Category findById(UUID id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(UUID id) {
        categoryRepository.deleteById(id);
    }
}
