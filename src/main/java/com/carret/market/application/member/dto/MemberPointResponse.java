package com.carret.market.application.member.dto;

import lombok.Getter;

@Getter
public class MemberPointResponse {

    private final int point;

    public MemberPointResponse(int point) {
        this.point = point;
    }
}
