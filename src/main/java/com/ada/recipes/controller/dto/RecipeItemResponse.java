package com.ada.recipes.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecipeItemResponse {
    private Integer id;
    private IngredientResponse ingredient;
    private Double quantity;
    private MeasuringUnitResponse measuringUnit;
}
