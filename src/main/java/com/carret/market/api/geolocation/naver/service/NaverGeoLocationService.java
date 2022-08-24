package com.carret.market.api.geolocation.naver.service;

import com.carret.market.api.geolocation.GeoLocationApi;
import com.carret.market.api.geolocation.dto.GeoLocationResponse;
import com.carret.market.api.geolocation.naver.NaverGeoLocationFeignClient;
import com.carret.market.api.geolocation.naver.dto.NaverGeoLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverGeoLocationService implements GeoLocationApi {
    private final NaverGeoLocationFeignClient naverGeoLocationFeignClient;

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String secretKey;

    @Override
    public GeoLocationResponse geoLocationApi(String coords) {
        NaverGeoLocationResponse naverGeoLocationResponse = naverGeoLocationFeignClient.reverseGeocodApi(clientId, secretKey, coords, "json");

        return new GeoLocationResponse(naverGeoLocationResponse.getArea());
    }
}
