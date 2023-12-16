package com.ada.recipes.service;

import com.ada.recipes.controller.dto.MeasuringUnitRequest;
import com.ada.recipes.controller.dto.MeasuringUnitResponse;
import com.ada.recipes.model.MeasuringUnit;
import com.ada.recipes.repository.MeasuringUnitRepository;
import com.ada.recipes.utils.MeasuringUnitConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MeasuringUnitService {
    @Autowired
    MeasuringUnitRepository repository;

    public Page<MeasuringUnitResponse> getMeasuringUnits(int page, int size, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "description");
        Page<MeasuringUnit> measuringUnitPage = repository.findAll(pageRequest);
        return MeasuringUnitConvert.toResponsePage(measuringUnitPage);
    }

    public MeasuringUnitResponse saveMeasuringUnit(MeasuringUnitRequest measuringUnitRequest){
        if(repository.findByDescriptionAndAbbreviation(measuringUnitRequest.getDescription(), measuringUnitRequest.getAbbreviation()).isPresent())
            throw new IllegalArgumentException("Unidade de medida " + measuringUnitRequest.getDescription() + "(" + measuringUnitRequest.getAbbreviation() + ") já está cadastrada!");
        MeasuringUnit measuringUnit = MeasuringUnitConvert.toEntity(measuringUnitRequest);
        MeasuringUnit measuringUnitEntity = repository.save(measuringUnit);
        return MeasuringUnitConvert.toResponse(measuringUnitEntity);
    }

    public MeasuringUnitResponse getMeasuringUnitById(Integer id){
        return MeasuringUnitConvert.toResponse(repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Unidade de medida não encontrada")));
    }
}
