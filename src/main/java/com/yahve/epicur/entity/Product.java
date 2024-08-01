package com.yahve.epicur.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(unique = true)
    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Name must not be null")
    private String name;

    private String description;

    @Positive(message = "Price must be positive")
    private double price;

    @PositiveOrZero(message = "Quantity must be zero or positive")
    private int quantity;

    @NotBlank(message = "imageUrl must not be blank")
    @NotNull(message = "imageUrl must not be null")
    private String imageUrl;

    private boolean onSiteOnly;
    private LocalDateTime created;
    private double rating;
    private boolean isVegan;

    @ManyToOne
    @JsonBackReference
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "product_ingredient",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;



    public boolean hasIngredients(List<String> ingredientNames) {
        for (String ingredientName : ingredientNames) {
            boolean found = false;
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
