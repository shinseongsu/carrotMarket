package com.carret.market.application.item.dto;

import static com.carret.market.application.item.dto.SubscriptCode.CANCEL;
import static com.carret.market.application.item.dto.SubscriptCode.SUBSCRIPT;

import lombok.Getter;

@Getter
public class SubscriptResult {

    private final SubscriptCode status;

    public SubscriptResult(SubscriptCode status) {
        this.status = status;
    }

    public static SubscriptResult subscript() {
        return new SubscriptResult(SUBSCRIPT);
    }

    public static SubscriptResult cancel() {
        return new SubscriptResult(CANCEL);
    }

}
