package com.ada.recipes.controller.dto;


import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class UserResponse {
    private Integer id;
    private String name;
    private String email;
}