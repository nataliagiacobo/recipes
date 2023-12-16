package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.RecipeRequest;
import com.ada.recipes.controller.dto.RecipeResponse;
import com.ada.recipes.controller.exception.InvalidRecipeItemsListExeption;
import com.ada.recipes.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @RequestMapping
    public ResponseEntity<Page<RecipeResponse>>getRecipes(
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
        return ResponseEntity.ok(recipeService.getRecipes(page, size, direction));
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> saveRecipe(
            @Valid @RequestBody RecipeRequest recipeDto
            ) throws Exception {
        RecipeResponse recipe = recipeService.saveRecipe(recipeDto);
        return ResponseEntity.created(URI.create("/recipe/"+recipe.getId())).body(recipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable Integer id){
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<RecipeResponse>>getRecipeByCategory(
            @PathVariable Integer category,
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
        return ResponseEntity.ok(recipeService.getRecipeByCategory(category, page, size, direction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(
            @PathVariable Integer id,
            @RequestBody RecipeRequest recipeRequest
    ) throws InvalidRecipeItemsListExeption {
        return ResponseEntity.ok(recipeService.saveRecipe(recipeRequest, id));
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Integer id){
        recipeService.deleteRecipe(id);
    }
}
