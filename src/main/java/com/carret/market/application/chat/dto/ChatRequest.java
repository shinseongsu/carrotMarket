package com.carret.market.application.chat.dto;

import lombok.Getter;

@Getter
public class ChatRequest {

    private final String message;

    public ChatRequest(String message) {
        this.message = message;
    }
}
