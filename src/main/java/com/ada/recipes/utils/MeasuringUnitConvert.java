package com.ada.recipes.utils;

import com.ada.recipes.controller.dto.MeasuringUnitRequest;
import com.ada.recipes.controller.dto.MeasuringUnitResponse;
import com.ada.recipes.model.MeasuringUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class MeasuringUnitConvert {

    public static MeasuringUnit toEntity(MeasuringUnitRequest measureRequest){
        MeasuringUnit measuringUnit = new MeasuringUnit();
        measuringUnit.setDescription(measureRequest.getDescription());
        measuringUnit.setAbbreviation(measureRequest.getAbbreviation());
        return measuringUnit;
    }

    public static MeasuringUnitResponse toResponse(MeasuringUnit measuringUnit){
        MeasuringUnitResponse measuringUnitResponse = new MeasuringUnitResponse();
        measuringUnitResponse.setId(measuringUnit.getId());
        measuringUnitResponse.setDescription(measuringUnit.getDescription());
        measuringUnitResponse.setAbbreviation(measuringUnit.getAbbreviation());
        return measuringUnitResponse;
    }

    public static List<MeasuringUnitResponse> toResponseList(List<MeasuringUnit> measuringUnitList){
        List<MeasuringUnitResponse> measurementUnitRespons = new ArrayList<>();
        for (MeasuringUnit measuringUnit : measuringUnitList) {
            MeasuringUnitResponse measuringUnitResponse = MeasuringUnitConvert.toResponse(measuringUnit);
            measurementUnitRespons.add(measuringUnitResponse);
        }
        return measurementUnitRespons;
    }

    public static Page<MeasuringUnitResponse> toResponsePage(Page<MeasuringUnit> measureList){
        List<MeasuringUnitResponse> measuringUnitResponseList = new ArrayList<>();
        for (MeasuringUnit measuringUnit : measureList) {
            MeasuringUnitResponse measuringUnitResponse = MeasuringUnitConvert.toResponse(measuringUnit);
            measuringUnitResponseList.add(measuringUnitResponse);
        }
        return new PageImpl<>(measuringUnitResponseList);
    }
}
