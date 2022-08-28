package com.carret.market.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class ChangeStatusDto {
    private String status;
    private String message;

    public static ChangeStatusDto ok() {
        return new ChangeStatusDto("ok", "변경에 성공하였습니다.");
    }
}
