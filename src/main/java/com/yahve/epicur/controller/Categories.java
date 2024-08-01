package com.yahve.epicur.controller;

import com.yahve.epicur.entity.Category;
import com.yahve.epicur.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class Categories {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories(
            @RequestParam(name = "sortBy", defaultValue = "rating") String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = "desc") String sortOrder,
            @RequestParam(name = "veganOnly", defaultValue = "false") boolean veganOnly,
            @RequestParam(name = "onSiteOnly", defaultValue = "false") boolean onSiteOnly,
            @RequestParam(name = "priceFrom", required = false) Double priceFrom,
            @RequestParam(name = "priceTo", required = false) Double priceTo,
            @RequestParam(required = false) String ingredients

    ) {

        List<String> ingredientList = (ingredients != null && !ingredients.isEmpty()) ? Arrays.asList(ingredients.split(",")) : null;
        return categoryService.getAllCategories(sortBy, sortOrder,veganOnly,onSiteOnly,priceFrom,priceTo,ingredientList);
    }

    @PostMapping
    public Category createCategory(@RequestBody @Valid Category category) {
        return categoryService.save(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteById(id);
    }
}
