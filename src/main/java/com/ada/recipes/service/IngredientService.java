package com.ada.recipes.service;

import com.ada.recipes.controller.dto.IngredientRequest;
import com.ada.recipes.controller.dto.IngredientResponse;
import com.ada.recipes.model.Ingredient;
import com.ada.recipes.repository.IngredientRepository;
import com.ada.recipes.utils.IngredientConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository repository;

    public Page<IngredientResponse> getIngredients(int page, int size, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "description");
        Page<Ingredient> recipeCategoryPage = repository.findAll(pageRequest);
        return IngredientConvert.toResponsePage(recipeCategoryPage);
    }

    public IngredientResponse saveIngredient(IngredientRequest ingredientRequest){
        if(repository.findByDescriptionIgnoreCase(ingredientRequest.getDescription()).isPresent())
            throw new IllegalArgumentException("Ingrediente " + ingredientRequest.getDescription() + " já está cadastrado!");
        Ingredient ingredient = IngredientConvert.toEntity(ingredientRequest);
        Ingredient ingredientEntity = repository.save(ingredient);
        return IngredientConvert.toResponse(ingredientEntity);
    }

    public IngredientResponse getIngredientById(Integer id){
        return IngredientConvert.toResponse(repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ingrediente não encontrado")));
    }

}
