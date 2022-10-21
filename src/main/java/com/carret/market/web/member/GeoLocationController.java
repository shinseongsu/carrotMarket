package com.carret.market.web.member;

import com.carret.market.application.geolocation.GeolocationService;
import com.carret.market.application.member.dto.GeoLocationRequest;
import com.carret.market.application.member.dto.GeoLocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeolocationService geolocationService;

    @PostMapping("/myGeolocation")
    public ResponseEntity<GeoLocationResponse> geolocationApi(@RequestBody GeoLocationRequest geoLocationRequestDto) {
        String area = geolocationService.searchGeolocation(geoLocationRequestDto);

        return ResponseEntity.ok().body(new GeoLocationResponse(area));
    }

}
