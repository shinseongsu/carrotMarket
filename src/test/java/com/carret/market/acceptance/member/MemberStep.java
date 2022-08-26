package com.carret.market.acceptance.member;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;

public class MemberStep {

    public static ExtractableResponse<Response> 회원_저장(String email) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("name", "test");
        params.put("nickname", "nickname");
        params.put("password", "password");
        params.put("passwordConfirm", "password");
        params.put("location", "서울시 강남구");

        return RestAssured
            .given().log().all()
            .queryParams(params)
            .when().post("/register")
            .then().log().all()
            .extract();
    }

    public static ExtractableResponse<Response> 로그인_후_정보_수정(String email,  String nickname) {
        Map<String, String> params = new HashMap<>();
        params.put("nickname", nickname);
        params.put("location", "경기도 성남시");

        return RestAssured
            .given().log().all()
            .auth().form(email, "password", new FormAuthConfig("/login", "email", "password"))
            .when().queryParams(params)
            .post("/myInfo/changeInfo")
            .then().log().all()
            .extract();
    }

}
