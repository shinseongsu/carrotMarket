package com.carret.market.web.member.dto;

import lombok.Getter;

@Getter
public class PointResponse {

    private boolean status;
    private String message;

    public PointResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
