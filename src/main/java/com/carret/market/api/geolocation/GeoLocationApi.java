package com.carret.market.api.geolocation;

import com.carret.market.api.geolocation.dto.GeoLocationResponse;

public interface GeoLocationApi {
    GeoLocationResponse geoLocationApi(String coords);
}
