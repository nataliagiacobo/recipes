package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.RecipeCategoryRequest;
import com.ada.recipes.controller.dto.RecipeCategoryResponse;
import com.ada.recipes.service.RecipeCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/recipeCategory")
public class RecipeCategoryController {
    @Autowired
    RecipeCategoryService service;

    @RequestMapping
    public ResponseEntity<Page<RecipeCategoryResponse>> getRecipeCategories(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0"
            ) int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10"
            ) int size,
            @RequestParam(
                    value = "direction",
                    required = false,
                    defaultValue = "ASC"
            ) String direction

    ){
        return ResponseEntity.ok(service.getRecipeCategories(page, size, direction));
    }

    @PostMapping
    public ResponseEntity<RecipeCategoryResponse> saveRecipeCategory(
            @Valid @RequestBody RecipeCategoryRequest categoryRequest
    ){
        RecipeCategoryResponse category = service.saveRecipeCategory(categoryRequest);
        return ResponseEntity.created(URI.create("/recipeCategory/"+category.getId())).body(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeCategoryResponse> getRecipeCategory(@PathVariable Integer id){
        return ResponseEntity.ok(service.getRecipeCategoryById(id));
    }

}
