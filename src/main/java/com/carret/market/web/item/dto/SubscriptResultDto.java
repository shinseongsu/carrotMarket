package com.carret.market.web.item.dto;

import static com.carret.market.web.item.dto.SubscriptCode.CANCEL;
import static com.carret.market.web.item.dto.SubscriptCode.SUBSCRIPT;

import lombok.Getter;

@Getter
public class SubscriptResultDto {
    private final SubscriptCode status;

    public SubscriptResultDto(SubscriptCode status) {
        this.status = status;
    }

    public static SubscriptResultDto subscript() {
        return new SubscriptResultDto(SUBSCRIPT);
    }

    public static SubscriptResultDto cancel() {
        return new SubscriptResultDto(CANCEL);
    }

}
