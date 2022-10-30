package com.carret.market.application.member.dto;

import lombok.Getter;

@Getter
public class SendPointResponse {

    private final String status;
    private final String message;

    public SendPointResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
