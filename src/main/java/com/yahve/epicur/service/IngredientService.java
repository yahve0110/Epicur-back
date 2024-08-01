package com.yahve.epicur.service;

import com.yahve.epicur.entity.Category;
import com.yahve.epicur.entity.Ingredient;
import com.yahve.epicur.exception.ResourceNotFoundException;
import com.yahve.epicur.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public  Ingredient addIngredient(Ingredient ingredient){
      return  ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients(){
       return ingredientRepository.findAll();
    };

    public Ingredient findById(UUID id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Ingredient getIngredientById(UUID ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
    }

    public void deleteById(UUID id) {
        ingredientRepository.deleteById(id);
    }
}
