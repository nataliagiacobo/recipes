package com.ada.recipes.service;

import com.ada.recipes.controller.dto.RecipeCategoryRequest;
import com.ada.recipes.controller.dto.RecipeCategoryResponse;
import com.ada.recipes.model.RecipeCategory;
import com.ada.recipes.repository.RecipeCategoryRepository;
import com.ada.recipes.utils.RecipeCategoryConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository repository;

    public Page<RecipeCategoryResponse> getRecipeCategories(int page, int size, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "description");
        Page<RecipeCategory> recipeCategoryPage = repository.findAll(pageRequest);
        return RecipeCategoryConvert.toResponsePage(recipeCategoryPage);
    }

    public RecipeCategoryResponse saveRecipeCategory(RecipeCategoryRequest categoryRequest){
        if(repository.findByDescriptionIgnoreCase(categoryRequest.getDescription()).isPresent())
            throw new IllegalArgumentException("Categoria " + categoryRequest.getDescription() + "já está cadastrada!");
        RecipeCategory category = RecipeCategoryConvert.toEntity(categoryRequest);
        RecipeCategory categoryEntity = repository.save(category);
        return RecipeCategoryConvert.toResponse(categoryEntity);
    }

    public RecipeCategoryResponse getRecipeCategoryById(Integer id){
        return RecipeCategoryConvert.toResponse(repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Categoria de receita não encontrada")));
    }

}
