package com.ada.recipes.utils;

import com.ada.recipes.controller.dto.IngredientRequest;
import com.ada.recipes.controller.dto.IngredientResponse;
import com.ada.recipes.model.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class IngredientConvert {

    public static Ingredient toEntity(IngredientRequest ingredientRequest){
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(ingredientRequest.getDescription());
        return ingredient;
    }

    public static IngredientResponse toResponse(Ingredient ingredient){
        IngredientResponse ingredientResponse = new IngredientResponse();
        ingredientResponse.setId(ingredient.getId());
        ingredientResponse.setDescription(ingredient.getDescription());
        return ingredientResponse;
    }

    public static List<IngredientResponse> toResponseList(List<Ingredient> ingredientList){
        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            IngredientResponse ingredientResponse = IngredientConvert.toResponse(ingredient);
            ingredientResponses.add(ingredientResponse);
        }
        return ingredientResponses;
    }

    public static Page<IngredientResponse> toResponsePage(Page<Ingredient> ingredientList){
        List<IngredientResponse> ingredientResponseList = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            IngredientResponse ingredientResponse = IngredientConvert.toResponse(ingredient);
            ingredientResponseList.add(ingredientResponse);
        }
        return new PageImpl<>(ingredientResponseList);
    }
}
