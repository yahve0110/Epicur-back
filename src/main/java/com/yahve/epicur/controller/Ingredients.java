package com.yahve.epicur.controller;

import com.yahve.epicur.entity.Ingredient;
import com.yahve.epicur.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/ingredients")
public class Ingredients {


    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public List<Ingredient> getAllIngredients(){
        return ingredientService.getAllIngredients();
    }

    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable UUID id) {
        ingredientService.deleteById(id);
    }
}
