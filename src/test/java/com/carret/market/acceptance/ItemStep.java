package com.carret.market.acceptance;

import com.carret.market.domain.item.Category;
import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;

public class ItemStep {

    private static final String multipartUrl = "/images/profile.png";

    public static ExtractableResponse<Response> 로그인_후_상품등록(String email, String title) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("title", title);
        params.put("category", Category.OTHER);
        params.put("price", 1000);
        params.put("description", "싸게 팝니다.");

        return RestAssured
            .given().log().all()
            .auth().form(email, "password", new FormAuthConfig("/login", "email", "password"))
            .when()
            .multiPart("imageUrl", new ClassPathResource(multipartUrl).getFile(), "multipart/form-data")
            .queryParams(params)
            .post("/item")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 로그인_후_상품_관심_주기(String email, Long itemId) {
        Map<String, Long> params = Map.of("itemId", itemId);

        return RestAssured
            .given().log().all()
            .auth().form(email, "password",  new FormAuthConfig("/login", "email", "password"))
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .when().post("/item/subscript")
            .then().log().all()
            .extract();
    }

}
