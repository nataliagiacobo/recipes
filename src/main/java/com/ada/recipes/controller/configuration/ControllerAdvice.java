package com.ada.recipes.controller.configuration;

import com.ada.recipes.controller.exception.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @Autowired
    private MessageSource messageSource;
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationError> handler(MethodArgumentNotValidException exception){
        List<ValidationError> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrors) {
            ValidationError error = new ValidationError();
            error.setField(fieldError.getField());
            error.setMessage(messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
            errors.add(error);
        }

        return errors;
    }
}