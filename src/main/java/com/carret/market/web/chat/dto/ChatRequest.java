package com.carret.market.web.chat.dto;

import lombok.Getter;

@Getter
public class ChatRequest {
    private final String message;

    public ChatRequest(String message) {
        this.message = message;
    }
}
