package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.MeasuringUnitRequest;
import com.ada.recipes.controller.dto.MeasuringUnitResponse;
import com.ada.recipes.service.MeasuringUnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/measuringUnit")
public class MeasuringUnitController {
    @Autowired
    MeasuringUnitService measuringUnitService;

    @RequestMapping
    public ResponseEntity<Page<MeasuringUnitResponse>>getMeasuringUnits(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0"
            ) int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10"
            ) int size,
            @RequestParam(
                    value = "direction",
                    required = false,
                    defaultValue = "ASC"
            ) String direction

    ){
        return ResponseEntity.ok(measuringUnitService.getMeasuringUnits(page, size, direction));
    }

    @PostMapping
    public ResponseEntity<MeasuringUnitResponse> saveMeasuringUnit(
            @Valid @RequestBody MeasuringUnitRequest measuringUnitDTO
    ) throws Exception {
        MeasuringUnitResponse measuringUnit = measuringUnitService.saveMeasuringUnit(measuringUnitDTO);
        return ResponseEntity.created(URI.create("/measuringUnit/"+measuringUnit.getId())).body(measuringUnit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeasuringUnitResponse> getMeasuringUnit(@PathVariable Integer id){
        return ResponseEntity.ok(measuringUnitService.getMeasuringUnitById(id));
    }
    
}