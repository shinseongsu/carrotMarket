package com.carret.market.application.api.geolocation.naver.dto;

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
