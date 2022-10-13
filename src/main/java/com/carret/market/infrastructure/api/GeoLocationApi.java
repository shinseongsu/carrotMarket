package com.carret.market.infrastructure.api;

import com.carret.market.infrastructure.api.dto.GeoLocationResponse;

public interface GeoLocationApi {
    GeoLocationResponse geoLocationApi(String coords);
}
