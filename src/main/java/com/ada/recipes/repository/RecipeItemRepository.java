package com.ada.recipes.repository;

import com.ada.recipes.model.RecipeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Integer> {
    @Override
    Page<RecipeItem> findAll(Pageable pageable);
}
