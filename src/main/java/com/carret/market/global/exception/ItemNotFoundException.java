package com.carret.market.global.exception;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
