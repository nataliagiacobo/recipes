package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.IngredientResponse;
import com.ada.recipes.service.IngredientService;
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
public class IngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IngredientService service;

    @Test
    public void should_register_ingredient() throws Exception {
        IngredientResponse response = new IngredientResponse();
        response.setId(100);
        response.setDescription("Ovo");

        when(service.saveIngredient(any())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/ingredient")
                                .content("""
                                    {
                                        "description": "Ovo"
                                    }
                                """)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()));
    }

    @Test
    public void should_get_ingredient_by_id() throws Exception {
        int id = 100;

        IngredientResponse response = new IngredientResponse();
        response.setId(id);
        response.setDescription("Ovo");

        when(service.getIngredientById(id)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/ingredient/" + id)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(response.getDescription()));
    }

    @Test
    public void should_list_all_existing_ingredients() throws Exception {
        int page = 0;
        int size = 10;
        String direction = "ASC";

        IngredientResponse response = new IngredientResponse();
        response.setId(100);
        response.setDescription("Bolos");

        Page<IngredientResponse> responsePage = new PageImpl<>(Collections.singletonList(response));

        when(service.getIngredients(page, size, direction)).thenReturn(responsePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/ingredient")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("direction", direction)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].description").value(response.getDescription()));
    }
}