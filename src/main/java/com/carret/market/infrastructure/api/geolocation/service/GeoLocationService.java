package com.carret.market.infrastructure.api.geolocation.service;

import com.carret.market.infrastructure.api.GeoLocationApi;
import com.carret.market.infrastructure.api.dto.GeoLocationResponse;
import com.carret.market.infrastructure.api.geolocation.NaverGeoLocationFeignClient;
import com.carret.market.infrastructure.api.geolocation.dto.NaverGeoLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoLocationService implements GeoLocationApi {
    private final NaverGeoLocationFeignClient naverGeoLocationFeignClient;

    private static final String JSON = "json";

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String secretKey;

    @Override
    public GeoLocationResponse geoLocationApi(String coords) {
        NaverGeoLocationResponse geoLocationResponse = naverGeoLocationFeignClient.reverseGeocodApi(clientId, secretKey, coords, JSON);

        return new GeoLocationResponse(geoLocationResponse.getArea());
    }
}
