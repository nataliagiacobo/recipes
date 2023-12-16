package com.ada.recipes.utils;

import com.ada.recipes.controller.dto.RecipeRequest;
import com.ada.recipes.controller.dto.RecipeResponse;
import com.ada.recipes.model.Recipe;
import com.ada.recipes.model.RecipeCategory;
import com.ada.recipes.model.RecipeItem;
import com.ada.recipes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class RecipeConvert {

    public static Recipe toEntity(RecipeRequest recipeRequest, User user, RecipeCategory recipeCategory, List<RecipeItem> items){
        Recipe recipe = new Recipe();
        recipe.setDescription(recipeRequest.getDescription());
        recipe.setCategory(recipeCategory);
        recipe.setPreparationTimeMin(recipeRequest.getPreparationTimeMin());
        recipe.setPreparationSteps(recipeRequest.getPreparationSteps());
        recipe.setItems(items);
        recipe.setUser(user);
        recipe.setActive(true);
        return recipe;
    }

    public static RecipeResponse toResponse(Recipe recipe){
        RecipeResponse recipeResponse = new RecipeResponse();
        recipeResponse.setId(recipe.getId());
        recipeResponse.setDescription(recipe.getDescription());
        recipeResponse.setRecipeCategory(RecipeCategoryConvert.toResponse(recipe.getCategory()));
        recipeResponse.setPreparationTimeMin(recipe.getPreparationTimeMin());
        for(RecipeItem item : recipe.getItems())
            recipeResponse.getItems().add(RecipeItemConvert.toResponse(item));
        recipeResponse.setPreparationSteps(recipe.getPreparationSteps());
        recipeResponse.setUser(UserConvert.toResponse(recipe.getUser()));
        return recipeResponse;
    }

    public static Page<RecipeResponse> toResponsePage(Page<Recipe> recipes){
        List<RecipeResponse> recipeResponseList = new ArrayList<>();
        for(Recipe recipe : recipes){
            RecipeResponse recipeResponse = toResponse(recipe);
            recipeResponseList.add(recipeResponse);
        }
        return new PageImpl<>(recipeResponseList);
    }
}
