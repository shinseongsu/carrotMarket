package com.carret.market.global.exception;

public class AlreadyItemStatusException extends RuntimeException {

    public AlreadyItemStatusException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
