package org.skypro.skyshop.model.error;

public enum ErrorCode {
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND"),
    BASKET_IS_EMPTY("BASKET_IS_EMPTY"),
    INVALID_INPUT("INVALID_INPUT"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}