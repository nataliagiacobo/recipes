package com.ada.recipes.repository;

import com.ada.recipes.model.RecipeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
    @Override
    Page<RecipeCategory> findAll(Pageable pageable);

    Optional<RecipeCategory> findByDescriptionIgnoreCase(String description);
}
