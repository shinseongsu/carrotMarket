package com.carret.market.global.exception;

public class FileUploadException extends RuntimeException {

    public FileUploadException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
