package com.ada.recipes.service;

import com.ada.recipes.controller.dto.RecipeItemRequest;
import com.ada.recipes.controller.dto.RecipeItemResponse;
import com.ada.recipes.model.Ingredient;
import com.ada.recipes.model.MeasuringUnit;
import com.ada.recipes.model.Recipe;
import com.ada.recipes.model.RecipeItem;
import com.ada.recipes.repository.IngredientRepository;
import com.ada.recipes.repository.MeasuringUnitRepository;
import com.ada.recipes.repository.RecipeItemRepository;
import com.ada.recipes.repository.RecipeRepository;
import com.ada.recipes.utils.RecipeItemConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeItemService {

    @Autowired
    RecipeItemRepository itemRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    MeasuringUnitRepository measuringUnitRepository;

    @Autowired
    RecipeRepository recipeRepository;

    public RecipeItem saveRecipeItem(RecipeItemRequest request){
        Ingredient ingredient = ingredientRepository.findById(request.getIngredientId()).get();
        MeasuringUnit measuringUnit = measuringUnitRepository.findById(request.getMeasuringUnitId()).get();
        RecipeItem recipeItem = RecipeItemConvert.toEntity(request, ingredient, measuringUnit);
        return itemRepository.save(recipeItem);
    }


}
