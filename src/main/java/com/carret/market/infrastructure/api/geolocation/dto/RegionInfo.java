package com.carret.market.infrastructure.api.geolocation.dto;

import java.util.StringJoiner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class RegionInfo {
    private AreaInfo area0;
    private AreaInfo area1;
    private AreaInfo area2;
    private AreaInfo area3;
    private AreaInfo area4;

    public String getArea() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add(area1.getName());
        stringJoiner.add(area2.getName());
        stringJoiner.add(area3.getName());
        stringJoiner.add(area4.getName());

        return stringJoiner.toString();
    }
}
