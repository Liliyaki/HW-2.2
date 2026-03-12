package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.error.ErrorCode;
import org.skypro.skyshop.model.error.ShopError;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProduct(NoSuchProductException e) {
        ShopError error = new ShopError(ErrorCode.PRODUCT_NOT_FOUND.getCode(), e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}