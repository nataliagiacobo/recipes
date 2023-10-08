package com.ada.recipes.repository;

import com.ada.recipes.model.Recipe;
import com.ada.recipes.model.RecipeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Page<Recipe> findAllByCategory(RecipeCategory category, Pageable pageable);

}
