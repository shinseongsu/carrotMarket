package com.carret.market.api.geolocation.naver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class NaverResultInfo {
    private String name;
    private RegionInfo region;

    public String getArea() {
        return region.getArea();
    }
}
