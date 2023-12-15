package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.MeasuringUnitResponse;
import com.ada.recipes.service.MeasuringUnitService;
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
public class MeasuringUnitControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MeasuringUnitService service;

    @Test
    public void should_register_measuring_unit() throws Exception {
        MeasuringUnitResponse response = new MeasuringUnitResponse();
        response.setId(100);
        response.setAbbreviation("KG");
        response.setDescription("quilograma");

        when(service.saveMeasuringUnit(any())).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/measuringUnit")
                        .content("""
                                    {
                                        "description": "quilograma",
                                        "abbreviation": "KG"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(response.getId()));
    }

    @Test
    public void should_get_unit_by_id() throws Exception {
        int id = 100;

        MeasuringUnitResponse response = new MeasuringUnitResponse();
        response.setId(100);
        response.setAbbreviation("KG");
        response.setDescription("quilograma");

        when(service.getMeasuringUnitById(id)).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/measuringUnit/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("description").value(response.getDescription())
                );
    }

    @Test
    public void should_list_all_existing_units() throws Exception {
        int page = 0;
        int size = 10;
        String direction = "ASC";

        MeasuringUnitResponse response = new MeasuringUnitResponse();
        response.setId(100);
        response.setAbbreviation("KG");
        response.setDescription("quilograma");

        Page<MeasuringUnitResponse> responsePage = new PageImpl<>(Collections.singletonList(response));

        when(service.getMeasuringUnits(page, size, direction)).thenReturn(responsePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/measuringUnit")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("direction", direction)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("content[0].description").value(response.getDescription()));
    }
}