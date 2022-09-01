package com.carret.market.application.api.geolocation.naver;

import com.carret.market.application.api.geolocation.naver.dto.NaverGeoLocationResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Qualifier("naverGeoLocationFeignClient")
@FeignClient(name = "naverGeoLocationFeignClient", url = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2")
public interface NaverGeoLocationFeignClient {

    @GetMapping("/gc")
    NaverGeoLocationResponse reverseGeocodApi(@RequestHeader("X-NCP-APIGW-API-KEY-ID") String clientId,
                                              @RequestHeader("X-NCP-APIGW-API-KEY") String secretKey,
                                              @RequestParam String coords,
                                              @RequestParam(defaultValue = "json") String output);

}
