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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}