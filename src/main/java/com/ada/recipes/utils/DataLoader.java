package com.ada.recipes.utils;

import com.ada.recipes.model.*;
import com.ada.recipes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeCategoryRepository categoryRepository;

    @Autowired
    private MeasuringUnitRepository measuringUnitRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeItemRepository recipeItemRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = new User(
                "Natalia",
                "natalia@email.com",
                passwordEncoder.encode("12345"),
                true);
        userRepository.save(user);

        RecipeCategory recipeCategory = new RecipeCategory("Bolos");
        categoryRepository.save(recipeCategory);

        RecipeCategory recipeCategory1 = new RecipeCategory("Tortas");
        categoryRepository.save(recipeCategory1);

        RecipeCategory recipeCategory2 = new RecipeCategory("Bebidas");
        categoryRepository.save(recipeCategory2);

        RecipeCategory recipeCategory3 = new RecipeCategory("Massas");
        categoryRepository.save(recipeCategory3);

        MeasuringUnit measuringUnit = new MeasuringUnit("quilograma", "kg");
        measuringUnitRepository.save(measuringUnit);

        MeasuringUnit measuringUnit1 = new MeasuringUnit("grama", "g");
        measuringUnitRepository.save(measuringUnit1);

        MeasuringUnit measuringUnit2 = new MeasuringUnit("mililitro", "ml");
        measuringUnitRepository.save(measuringUnit2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setDescription("Leite");
        ingredientRepository.save(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setDescription("Chocolate em pó");
        ingredientRepository.save(ingredient2);


        RecipeItem recipeItem1 = new RecipeItem();
        recipeItem1.setIngredient(ingredient1);
        recipeItem1.setQuantity(200.0);
        recipeItem1.setMeasuringUnit(measuringUnit2);

        RecipeItem recipeItem2 = new RecipeItem();
        recipeItem2.setIngredient(ingredient2);
        recipeItem2.setQuantity(20.0);
        recipeItem2.setMeasuringUnit(measuringUnit1);

        Recipe recipe = new Recipe();
        recipe.setDescription("Leite com chocolate");
        recipe.setCategory(recipeCategory2);
        recipe.setPreparationTimeMin(10);
        recipe.setUser(user);
        recipe.getPreparationSteps().add("Misture os ingredientes.");
        recipe.getPreparationSteps().add("Beba!");
        recipe.setActive(true);
        recipe.setItems(new ArrayList<>());
        recipe.getItems().add(recipeItem1);
        recipe.getItems().add(recipeItem2);
        recipeRepository.save(recipe);

        for(RecipeItem item : recipe.getItems())
            item.setRecipe(recipe);

        recipeRepository.save(recipe);


    }
}