package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.IngredientRequest;
import com.ada.recipes.controller.dto.IngredientResponse;
import com.ada.recipes.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    @Autowired
    IngredientService service;

    @RequestMapping
    public ResponseEntity<Page<IngredientResponse>> getIngredients(
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
        return ResponseEntity.ok(service.getIngredients(page, size, direction));
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> saveIngredient(
            @Valid @RequestBody IngredientRequest ingredientRequest
    ){
        IngredientResponse category = service.saveIngredient(ingredientRequest);
        return ResponseEntity.created(URI.create("/ingredient/"+category.getId())).body(category);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getIngredient(@PathVariable Integer id){
        return ResponseEntity.ok(service.getIngredientById(id));
    }

}
