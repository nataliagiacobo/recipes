package com.ada.recipes.service;

import com.ada.recipes.controller.dto.*;
import com.ada.recipes.model.*;
import com.ada.recipes.repository.*;
import com.ada.recipes.utils.RecipeConvert;
import com.ada.recipes.utils.RecipeItemConvert;
import com.ada.recipes.utils.UserConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeCategoryRepository categoryRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    MeasuringUnitRepository measuringUnitRepository;
    @Autowired
    UserRepository userRepository;

    public RecipeResponse saveRecipe(RecipeRequest recipeRequest){
        return saveRecipe(recipeRequest, null);
    }
    public RecipeResponse saveRecipe(RecipeRequest recipeRequest, Integer id){
        User user = userRepository.findById(recipeRequest.getUserId()).get();
        RecipeCategory category = categoryRepository.findById(recipeRequest.getRecipeCategoryId()).get();
        List<RecipeItem> recipeItemList = new ArrayList<>();
        for (RecipeItemRequest itemRequest : recipeRequest.getItems()){
            recipeItemList.add(RecipeItemConvert.toEntity(
                    itemRequest,
                    ingredientRepository.findById(itemRequest.getIngredientId()).get(),
                    measuringUnitRepository.findById(itemRequest.getMeasuringUnitId()).get()));
        }
        Recipe recipe = RecipeConvert.toEntity(recipeRequest, user, category, recipeItemList);
        recipe.setId(id);
        recipe.setActive(true);
        recipeRepository.save(recipe);

        for (RecipeItem item : recipe.getItems()){
            item.setRecipe(recipe);
        }
        return RecipeConvert.toResponse(recipeRepository.save(recipe));
    }

    public Page<RecipeResponse> getRecipes(int page, int size, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "description");
        Page<Recipe> recipes = recipeRepository.findAll(pageRequest);
        return RecipeConvert.toResponsePage(recipes);
    }

    public RecipeResponse getRecipeById(Integer id){
        return RecipeConvert.toResponse(recipeRepository.findById(id).orElseThrow());
    }

    public Page<RecipeResponse> getRecipeByCategory(Integer id, int page, int size, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "description");
        RecipeCategory category = categoryRepository.findById(id).get();
        Page<Recipe> recipes = recipeRepository.findAllByCategory(category, pageRequest);
        return RecipeConvert.toResponsePage(recipes);
    }

    public void deleteRecipe (Integer id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        recipe.setActive(false);
        recipeRepository.save(recipe);
    }

}