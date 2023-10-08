package com.ada.recipes.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeResponse {
    private Integer id;
    private String description;
    private RecipeCategoryResponse recipeCategory;
    private Integer preparationTimeMin;
    private List<RecipeItemResponse> items = new ArrayList<>();
    private List<String> preparationSteps = new ArrayList<>();
    private UserResponse user;
}
