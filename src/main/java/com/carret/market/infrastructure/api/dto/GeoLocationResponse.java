package com.carret.market.infrastructure.api.dto;

import lombok.Getter;

@Getter
public class GeoLocationResponse {
    private String area;

    public GeoLocationResponse(String area) {
        this.area = area;
    }
}
