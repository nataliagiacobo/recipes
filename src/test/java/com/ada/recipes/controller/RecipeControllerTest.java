package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.RecipeResponse;
import com.ada.recipes.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeService service;

    @Test
    public void should_register_recipe() throws Exception {
        RecipeResponse response = new RecipeResponse();
        response.setId(100);
        response.setDescription("Ovo mexido");

        when(service.saveRecipe(any())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/recipe")
                                .content("""
                                            {
                                                "description": "Ovo mexido",
                                                "recipeCategoryId": 5,
                                                "preparationTimeMin": 10,
                                                "items": [
                                                    {
                                                        "ingredientId": 3,
                                                        "quantity": 2.0,
                                                        "measuringUnitId": 4
                                                    }
                                                ],
                                                "preparationSteps": [
                                                    "Quebre os ovos.",
                                                    "Mexa em fogo baixo.",
                                                    "Coma"
                                                ],
                                                "userId": 1
                                            }
                                        """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()));
    }

    @Test
    public void should_get_recipe_by_id() throws Exception {
        int id = 100;

        RecipeResponse response = new RecipeResponse();
        response.setId(id);
        response.setDescription("Ovo mexido");

        when(service.getRecipeById(id)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/recipe/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(response.getDescription()));
    }

    @Test
    public void should_list_all_existing_recipes() throws Exception {
        int page = 0;
        int size = 10;
        String direction = "ASC";

        RecipeResponse response = new RecipeResponse();
        response.setId(100);
        response.setDescription("Ovo mexido");

        Page<RecipeResponse> responsePage = new PageImpl<>(Collections.singletonList(response));

        when(service.getRecipes(page, size, direction)).thenReturn(responsePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("direction", direction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].description").value(response.getDescription()));
    }
}