package com.ada.recipes.service;

import com.ada.recipes.controller.dto.RecipeCategoryRequest;
import com.ada.recipes.controller.dto.RecipeCategoryResponse;
import com.ada.recipes.model.RecipeCategory;
import com.ada.recipes.repository.RecipeCategoryRepository;
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
public class RecipeCategoryServiceTest {
    @InjectMocks
    private RecipeCategoryService service;
    @Mock
    private RecipeCategoryRepository repository;

    @Test
    public void valid_category_should_register_with_sucess() {
        RecipeCategory category = new RecipeCategory("Bolos");

        RecipeCategoryRequest categoryRequest = new RecipeCategoryRequest(category.getDescription());

        when(repository.save(any())).thenReturn(category);

        service.saveRecipeCategory(categoryRequest);
    }

    @Test
    public void should_not_register_duplicated_category() {
        RecipeCategory category = new RecipeCategory("Bolos");

        RecipeCategoryRequest categoryRequest = new RecipeCategoryRequest(category.getDescription());

        when(repository.findByDescriptionIgnoreCase(any())).thenReturn(Optional.of(category));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveRecipeCategory(categoryRequest)
        );
    }
    @Test
    public void search_existing_category_should_return_category() {
        RecipeCategory category = new RecipeCategory("Bolos");

        when(repository.findById(1)).thenReturn(Optional.of(category));

        RecipeCategoryResponse response = service.getRecipeCategoryById(1);
        assertNotNull(response);
    }
    @Test
    public void search_non_existing_category_should_return_error() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> service.getRecipeCategoryById(1)
        );
    }

    @Test
    public void list_of_categories_should_return_with_all_itens(){
        int page = 0;
        int size = 10;
        String direction = "ASC";

        List<RecipeCategory> list = new ArrayList<>();
        list.add(new RecipeCategory(1, "Bolos"));
        list.add(new RecipeCategory(2, "Biscoitos"));

        Page<RecipeCategory> categoryPage = new PageImpl<>(list);

        when(repository.findAll(any(Pageable.class))).thenReturn(categoryPage);

        Page<RecipeCategoryResponse> result = service.getRecipeCategories(page, size, direction);

        assertNotNull(result);
        assertEquals(list.size(), result.getContent().size());
    }
}