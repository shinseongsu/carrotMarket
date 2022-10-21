package com.carret.market.global.exception;

public class MemberNotFoundException extends RuntimeException{

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
