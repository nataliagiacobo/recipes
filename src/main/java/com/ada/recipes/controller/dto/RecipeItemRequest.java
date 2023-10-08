package com.ada.recipes.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeItemRequest {
    private Integer ingredientId;
    private Double quantity;
    private Integer measuringUnitId;
    private Integer recipeId;
}
