package com.carret.market.web.member;

import com.carret.market.service.geolocation.GeolocationService;
import com.carret.market.web.member.dto.GeoLocationRequestDto;
import com.carret.market.web.member.dto.GeoLocationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GeoLocationController {

    private final GeolocationService geolocationService;

    @PostMapping("/myGeolocation")
    public ResponseEntity<GeoLocationResponseDto> geolocationApi(@RequestBody GeoLocationRequestDto geoLocationRequestDto) {
        String area = geolocationService.searchGeolocation(geoLocationRequestDto);

        return ResponseEntity.ok().body(new GeoLocationResponseDto(area));
    }

}
