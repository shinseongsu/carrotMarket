package com.carret.market.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND_MEMBER("회원이 존재하지 않습니다."),
    NOT_FOUND_ITEM("존재하지 않는 상품입니다."),
    NOT_FOUND_USERNAME("존재하지 않는 아이디 입니다."),

    ALREADY_ITEM_SOLD("아이템이 품절된 상태입니다."),

    EXCEPTION_FILE_UPLOAD("파일 업로드중 에러가 발생하였습니다."),
    AT_LEAST_ONE_IMAGE("하나 이상의 이미지가 필요합니다."),

    FAIL_LOGIN("로그인 실패하였습니다."),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
