package com.carret.market.application.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PayFormResponse {

    private Long sellerId;
    private int amount;

    public PayFormResponse(Long sellerId, int amount) {
        this.sellerId = sellerId;
        this.amount = amount;
    }
}
