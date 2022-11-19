package com.carret.market.application.item.dto;

import lombok.Getter;

@Getter
public class ConfirmResponse {

    private final String message;

    public ConfirmResponse(String message) {
        this.message = message;
    }
}
