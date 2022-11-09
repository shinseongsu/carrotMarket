package com.carret.market.Integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.carret.market.application.geolocation.GeolocationService;
import com.carret.market.application.member.dto.GeoLocationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GeolocationServiceTest extends IntegrationTest {

    @Autowired
    private GeolocationService geolocationService;

    private GeoLocationRequest geoLocationRequest;

    @DisplayName("위도 경로로 주소 반환한다.")
    @Test
    void searchGeolocationTest() {
        geoLocationRequest = new GeoLocationRequest("126.975155,37.2157014");

        String area = geolocationService.searchGeolocation(geoLocationRequest);

        assertThat(area).isEqualTo("경기도 화성시 봉담읍 와우리");
    }

}
