package com.carret.market.api.geolocation.naver.dto;

import com.carret.market.global.exception.EmptyGeoLocationException;
import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NaverGeoLocationResponse {
    private NaverStatus status;
    private NaverResultInfo[] results;

    private static String EMPTY_RESULT = "API 요청 결과 값이 없습니다.";

    public String getArea() {
        return Arrays.stream(results)
            .map(result -> result.getArea())
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new EmptyGeoLocationException(EMPTY_RESULT));
    }
}
