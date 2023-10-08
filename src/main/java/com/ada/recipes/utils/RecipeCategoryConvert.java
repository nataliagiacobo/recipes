package com.ada.recipes.utils;

import com.ada.recipes.controller.dto.RecipeCategoryRequest;
import com.ada.recipes.controller.dto.RecipeCategoryResponse;
import com.ada.recipes.model.RecipeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class RecipeCategoryConvert {

    public static RecipeCategory toEntity(RecipeCategoryRequest categoryRequest){
        RecipeCategory category = new RecipeCategory();
        category.setDescription(categoryRequest.getDescription());
        return category;
    }

    public static RecipeCategoryResponse toResponse(RecipeCategory category){
        RecipeCategoryResponse categoryResponse = new RecipeCategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setDescription(category.getDescription());
        return categoryResponse;
    }

    public static List<RecipeCategoryResponse> toResponseList(List<RecipeCategory> categoryList){
        List<RecipeCategoryResponse> categoryResponses = new ArrayList<>();
        for (RecipeCategory category : categoryList) {
            RecipeCategoryResponse categoryResponse = RecipeCategoryConvert.toResponse(category);
            categoryResponses.add(categoryResponse);
        }
        return categoryResponses;
    }

    public static Page<RecipeCategoryResponse> toResponsePage(Page<RecipeCategory> categoryList){
        List<RecipeCategoryResponse> categoryResponseList = new ArrayList<>();
        for (RecipeCategory category : categoryList) {
            RecipeCategoryResponse categoryResponse = RecipeCategoryConvert.toResponse(category);
            categoryResponseList.add(categoryResponse);
        }
        return new PageImpl<>(categoryResponseList);
    }
}
