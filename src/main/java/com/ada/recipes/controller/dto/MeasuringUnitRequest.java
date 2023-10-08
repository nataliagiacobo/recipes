package com.ada.recipes.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
@Getter
@AllArgsConstructor
public class MeasuringUnitRequest {
    @NotBlank
    @Length(min = 5)
    private String description;
    @NotBlank
    @Length(min = 1)
    private String abbreviation;
}
