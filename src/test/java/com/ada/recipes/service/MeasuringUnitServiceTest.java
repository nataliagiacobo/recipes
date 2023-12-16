package com.ada.recipes.service;

import com.ada.recipes.controller.dto.MeasuringUnitRequest;
import com.ada.recipes.controller.dto.MeasuringUnitResponse;
import com.ada.recipes.model.MeasuringUnit;
import com.ada.recipes.repository.MeasuringUnitRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MeasuringUnitServiceTest {
    @InjectMocks
    private MeasuringUnitService service;
    @Mock
    private MeasuringUnitRepository repository;

    @Test
    public void valid_unit_should_register_with_sucess() {
        MeasuringUnit unit = new MeasuringUnit(1,"grama", "g");

        MeasuringUnitRequest unitRequest = new MeasuringUnitRequest(unit.getDescription(), unit.getAbbreviation());

        when(repository.save(any())).thenReturn(unit);

        service.saveMeasuringUnit(unitRequest);
    }

    @Test
    public void should_not_register_duplicated_unit() {
        MeasuringUnit unit = new MeasuringUnit(1,"grama", "g");

        MeasuringUnitRequest unitRequest = new MeasuringUnitRequest(unit.getDescription(), unit.getAbbreviation());

        when(repository.findByDescriptionAndAbbreviation(any(),any())).thenReturn(Optional.of(unit));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveMeasuringUnit(unitRequest)
        );
    }
    @Test
    public void search_existing_unit_should_return_unit() {
        MeasuringUnit unit = new MeasuringUnit(1,"grama", "g");

        when(repository.findById(1)).thenReturn(Optional.of(unit));

        MeasuringUnitResponse response = service.getMeasuringUnitById(1);
        assertNotNull(response);
    }
    @Test
    public void search_non_existing_unit_should_return_error() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> service.getMeasuringUnitById(1)
        );
    }

    @Test
    public void list_of_units_should_return_with_all_items(){
        int page = 0;
        int size = 10;
        String direction = "ASC";

        List<MeasuringUnit> list = new ArrayList<>();
        list.add(new MeasuringUnit(1, "grama", "g"));
        list.add(new MeasuringUnit(2, "litro", "l"));

        Page<MeasuringUnit> unitPage = new PageImpl<>(list);

        when(repository.findAll(any(Pageable.class))).thenReturn(unitPage);

        Page<MeasuringUnitResponse> result = service.getMeasuringUnits(page, size, direction);

        assertNotNull(result);
        assertEquals(list.size(), result.getContent().size());
    }
}