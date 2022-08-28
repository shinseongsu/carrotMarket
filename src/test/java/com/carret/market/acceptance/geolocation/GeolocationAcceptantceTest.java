package com.carret.market.acceptance.geolocation;

import static com.carret.market.acceptance.geolocation.GeoLocationStep.좌표로_위치_검색;
import static org.assertj.core.api.Assertions.assertThat;

import com.carret.market.acceptance.AcceptanceTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("주소 찾는 API")
public class GeolocationAcceptantceTest extends AcceptanceTest {

    @Test
    void geolocationApi() {
        ExtractableResponse<Response> response = 좌표로_위치_검색("126.975155,37.2157014");

        assertThat(response.jsonPath().getString("area")).isEqualTo("경기도 화성시 봉담읍 와우리");
    }

}
