package com.carret.market.infrastructure.api.geolocation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class NaverStatus {
    private Integer code;
    private String name;
    private String message;
}
