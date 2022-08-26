package com.carret.market.acceptance.geolocation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;

public class GeoLocationStep {

    public static ExtractableResponse<Response> 좌표로_위치_검색(String coords) {
        Map<String, String> params = new HashMap<>();
        params.put("coords", coords);

        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .when().post("/myGeolocation")
            .then().log().all()
            .extract();
    }
}
