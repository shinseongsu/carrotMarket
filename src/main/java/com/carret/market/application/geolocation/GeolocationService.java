package com.carret.market.application.geolocation;

import com.carret.market.infrastructure.api.GeoLocationApi;
import com.carret.market.web.member.dto.GeoLocationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    private final GeoLocationApi geoLocationApi;

    public String searchGeolocation(GeoLocationRequestDto geoLocationRequestDto) {
        return geoLocationApi.geoLocationApi(geoLocationRequestDto.getCoords()).getArea();
    }
}
