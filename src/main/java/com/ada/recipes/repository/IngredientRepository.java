package com.ada.recipes.repository;

import com.ada.recipes.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    @Override
    Page<Ingredient> findAll(Pageable pageable);

    Optional<Ingredient> findByDescriptionIgnoreCase(String description);
}
