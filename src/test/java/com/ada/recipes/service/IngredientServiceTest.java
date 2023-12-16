package com.ada.recipes.service;

import com.ada.recipes.controller.dto.IngredientRequest;
import com.ada.recipes.controller.dto.IngredientResponse;
import com.ada.recipes.model.Ingredient;
import com.ada.recipes.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class IngredientServiceTest {
    @InjectMocks
    private IngredientService service;
    @Mock
    private IngredientRepository repository;

    @Test
    public void valid_ingredient_should_register_with_sucess() {
        Ingredient ingredient = new Ingredient(1, "Ovo");

        IngredientRequest ingredientRequest = new IngredientRequest(ingredient.getDescription());

        when(repository.save(any())).thenReturn(ingredient);

        service.saveIngredient(ingredientRequest);
    }

    @Test
    public void should_not_register_duplicated_ingredient() {
        Ingredient ingredient = new Ingredient(1, "Ovo");

        IngredientRequest ingredientRequest = new IngredientRequest(ingredient.getDescription());

        when(repository.findByDescriptionIgnoreCase(any())).thenReturn(Optional.of(ingredient));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveIngredient(ingredientRequest)
        );
    }
    @Test
    public void search_existing_ingredient_should_return_ingredient() {
        Ingredient ingredient = new Ingredient(1, "Ovo");

        when(repository.findById(1)).thenReturn(Optional.of(ingredient));

        IngredientResponse response = service.getIngredientById(1);
        assertNotNull(response);
    }
    @Test
    public void search_non_existing_ingredient_should_return_error() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> service.getIngredientById(1)
        );
    }

    @Test
    public void list_of_ingredients_should_return_with_all_itens(){
        int page = 0;
        int size = 10;
        String direction = "ASC";

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "Ovo"));
        ingredients.add(new Ingredient(2, "Farinha de Trigo"));

        Page<Ingredient> ingredientPage = new PageImpl<>(ingredients);

        when(repository.findAll(any(Pageable.class))).thenReturn(ingredientPage);

        Page<IngredientResponse> result = service.getIngredients(page, size, direction);

        assertNotNull(result);
        assertEquals(ingredients.size(), result.getContent().size());
    }

}