package com.carret.market.application.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeStatus {

    private String status;
    private String message;

    public static ChangeStatus ok() {
        return new ChangeStatus("ok", "변경에 성공하였습니다.");
    }
}
