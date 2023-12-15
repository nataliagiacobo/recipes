package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.RecipeCategoryResponse;
import com.ada.recipes.service.RecipeCategoryService;
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
public class RecipeCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RecipeCategoryService service;

    @Test
    public void should_register_recipe_category() throws Exception {
        RecipeCategoryResponse response = new RecipeCategoryResponse();
        response.setId(100);
        response.setDescription("Bolos");

        when(service.saveRecipeCategory(any())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/recipeCategory")
                                .content("""
                                    {
                                        "description": "Bolos"
                                    }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()));
    }

    @Test
    public void should_get_category_by_id() throws Exception {
        int id = 100;

        RecipeCategoryResponse response = new RecipeCategoryResponse();
        response.setId(id);
        response.setDescription("Bolos");

        when(service.getRecipeCategoryById(id)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/recipeCategory/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(response.getDescription()));
    }

    @Test
    public void should_list_all_existing_categories() throws Exception {
        int page = 0;
        int size = 10;
        String direction = "ASC";

        RecipeCategoryResponse response = new RecipeCategoryResponse();
        response.setId(100);
        response.setDescription("Bolos");

        Page<RecipeCategoryResponse> responsePage = new PageImpl<>(Collections.singletonList(response));

        when(service.getRecipeCategories(page, size, direction)).thenReturn(responsePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipeCategory")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("direction", direction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].description").value(response.getDescription()));
    }
}