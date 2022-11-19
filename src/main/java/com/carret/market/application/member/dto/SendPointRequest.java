package com.carret.market.application.member.dto;

import lombok.Getter;

@Getter
public class SendPointRequest {

    private final Long sendId;
    private final int amount;
    private final Long roomId;

    public SendPointRequest(Long sendId, int amount, Long roomId) {
        this.sendId = sendId;
        this.amount = amount;
        this.roomId = roomId;
    }
}
