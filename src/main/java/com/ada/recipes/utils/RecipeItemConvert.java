package com.ada.recipes.utils;

import com.ada.recipes.controller.dto.*;
import com.ada.recipes.model.Ingredient;
import com.ada.recipes.model.MeasuringUnit;
import com.ada.recipes.model.Recipe;
import com.ada.recipes.model.RecipeItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class RecipeItemConvert {

    public static RecipeItem toEntity(RecipeItemRequest itemRequest, Ingredient ingredient, MeasuringUnit measuringUnit){
        RecipeItem recipeItem = new RecipeItem();
        recipeItem.setIngredient(ingredient);
        recipeItem.setQuantity(itemRequest.getQuantity());
        recipeItem.setMeasuringUnit(measuringUnit);
        return recipeItem;
    }

    public static RecipeItemResponse toResponse(RecipeItem recipeItem){
        RecipeItemResponse itemResponse = new RecipeItemResponse();
        itemResponse.setId(recipeItem.getId());
        itemResponse.setIngredient(IngredientConvert.toResponse(recipeItem.getIngredient()));
        itemResponse.setQuantity(recipeItem.getQuantity());
        itemResponse.setMeasuringUnit(MeasuringUnitConvert.toResponse(recipeItem.getMeasuringUnit()));
        return itemResponse;
    }

    public static Page<RecipeItemResponse> toResponsePage(Page<RecipeItem> items){
        List<RecipeItemResponse> itemResponseList = new ArrayList<>();
        for (RecipeItem item : items) {
            RecipeItemResponse itemResponse = toResponse(item);
            itemResponseList.add(itemResponse);
        }
        return new PageImpl<>(itemResponseList);
    }
}
