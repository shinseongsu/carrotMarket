package com.carret.market.application.api.geolocation;

import com.carret.market.application.api.geolocation.dto.GeoLocationResponse;

public interface GeoLocationApi {
    GeoLocationResponse geoLocationApi(String coords);
}
