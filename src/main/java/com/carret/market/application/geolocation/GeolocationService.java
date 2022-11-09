package com.carret.market.application.geolocation;

import com.carret.market.infrastructure.api.GeoLocationApi;
import com.carret.market.application.member.dto.GeoLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeolocationService {
    private final GeoLocationApi geoLocationApi;

    public String searchGeolocation(GeoLocationRequest geoLocationRequest) {
        return geoLocationApi.geoLocationApi(geoLocationRequest.getCoords()).getArea();
    }
}
