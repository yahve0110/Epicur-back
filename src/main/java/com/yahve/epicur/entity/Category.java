package com.yahve.epicur.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(unique=true)
    @NotBlank(message = "Category name must not be blank")
    @NotNull(message = "Category name must not be null")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Product> products;
}
