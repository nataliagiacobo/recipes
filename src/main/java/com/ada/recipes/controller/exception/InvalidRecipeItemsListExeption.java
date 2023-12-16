package com.ada.recipes.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidRecipeItemsListExeption extends Exception{
    private String description;
}
