package com.ada.recipes.utils;

public class Validator {

    public static Boolean passwordValidate(String password){
        /*
        Pelo menos uma letra maiúscula
        Pelo menos um caractere especial
        Pelo menos um número
        No mínimo 8 caracteres
         */
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$");
    }
}
