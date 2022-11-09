package com.carret.market.application.member.dto;

import lombok.Getter;

@Getter
public class PointResponse {

    private final boolean status;
    private final String message;

    public PointResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
