package com.ada.recipes.service;

import com.ada.recipes.controller.dto.RecipeItemRequest;
import com.ada.recipes.controller.dto.RecipeRequest;
import com.ada.recipes.controller.exception.InvalidRecipeItemsListExeption;
import com.ada.recipes.model.*;
import com.ada.recipes.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RecipeServiceTest {
    @InjectMocks
    private RecipeService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RecipeCategoryRepository recipeCategoryRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private MeasuringUnitRepository measuringUnitRepository;

    @Test
    public void should_not_register_recipe_with_non_existing_user() {
        RecipeRequest request = new RecipeRequest();
        request.setDescription("Bolo de Cenoura");
        request.setRecipeCategoryId(1);
        request.setUserId(1);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveRecipe(request)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    public void should_not_register_recipe_with_non_existing_category() {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RecipeRequest request = new RecipeRequest();
        request.setDescription("Bolo de Cenoura");
        request.setRecipeCategoryId(1);
        request.setUserId(1);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveRecipe(request)
        );

        assertEquals("Categoria de receita não encontrada", exception.getMessage());
    }

    @Test
    public void should_not_register_recipe_without_items_list() {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RecipeCategory category = new RecipeCategory();
        category.setId(1);
        category.setDescription("Bolos");

        when(recipeCategoryRepository.findById(1)).thenReturn(Optional.of(category));

        RecipeRequest request = new RecipeRequest();
        request.setDescription("Bolo de Cenoura");
        request.setRecipeCategoryId(1);
        request.setUserId(1);
        request.setItems(new ArrayList<>());

        InvalidRecipeItemsListExeption exception = Assertions.assertThrows(InvalidRecipeItemsListExeption.class,
                () -> service.saveRecipe(request)
        );

        assertEquals("Lista de itens está vazia", exception.getDescription());
    }

    @Test
    public void should_not_register_recipe_with_non_existing_ingredient() {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RecipeCategory category = new RecipeCategory();
        category.setId(1);
        category.setDescription("Bolos");

        when(recipeCategoryRepository.findById(1)).thenReturn(Optional.of(category));

        RecipeItemRequest item = new RecipeItemRequest();
        item.setIngredientId(1);
        item.setMeasuringUnitId(1);
        item.setQuantity(100D);

        RecipeRequest request = new RecipeRequest();
        request.setDescription("Bolo de Cenoura");
        request.setRecipeCategoryId(1);
        request.setUserId(1);
        request.setItems(new ArrayList<>());
        request.getItems().add(item);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveRecipe(request)
        );

        assertEquals("Ingrediente não encontrado", exception.getMessage());
    }

    @Test
    public void should_not_register_recipe_with_non_existing_unit() {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RecipeCategory category = new RecipeCategory();
        category.setId(1);
        category.setDescription("Bolos");

        when(recipeCategoryRepository.findById(1)).thenReturn(Optional.of(category));

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setDescription("Ovo");

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));

        RecipeItemRequest item = new RecipeItemRequest();
        item.setIngredientId(1);
        item.setMeasuringUnitId(1);
        item.setQuantity(100D);

        RecipeRequest request = new RecipeRequest();
        request.setDescription("Bolo de Cenoura");
        request.setRecipeCategoryId(1);
        request.setUserId(1);
        request.setItems(new ArrayList<>());
        request.getItems().add(item);

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveRecipe(request)
        );

        assertEquals("Unidade de medida não encontrada", exception.getMessage());
    }

    @Test
    public void new_recipe_should_register_with_success() throws InvalidRecipeItemsListExeption {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        RecipeCategory category = new RecipeCategory();
        category.setId(1);
        category.setDescription("Bolos");

        when(recipeCategoryRepository.findById(1)).thenReturn(Optional.of(category));

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setDescription("Ovo");

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));

        MeasuringUnit unit = new MeasuringUnit();
        unit.setId(1);
        unit.setDescription("unidade");
        unit.setAbbreviation("unid");

        when(measuringUnitRepository.findById(1)).thenReturn(Optional.of(unit));

        RecipeItemRequest item = new RecipeItemRequest();
        item.setIngredientId(1);
        item.setMeasuringUnitId(1);
        item.setQuantity(100D);

        RecipeRequest request = new RecipeRequest();
        request.setDescription("Bolo de Cenoura");
        request.setRecipeCategoryId(1);
        request.setUserId(1);
        request.setItems(new ArrayList<>());
        request.getItems().add(item);

        RecipeItem recipeItem = new RecipeItem();
        recipeItem.setMeasuringUnit(unit);
        recipeItem.setIngredient(ingredient);
        recipeItem.setQuantity(100D);

        Recipe recipe = new Recipe();
        recipe.setId(1);
        recipe.setUser(user);
        recipe.setCategory(category);
        recipe.setItems(new ArrayList<>());
        recipe.getItems().add(recipeItem);

        when(recipeRepository.save(any())).thenReturn(recipe);

        service.saveRecipe(request);

    }

}