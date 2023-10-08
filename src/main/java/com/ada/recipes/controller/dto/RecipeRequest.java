package com.ada.recipes.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RecipeRequest {
    private String description;
    private Integer recipeCategoryId;
    private Integer preparationTimeMin;
    private List<RecipeItemRequest> items;
    private List<String> preparationSteps;
    private Integer userId;
}
