package com.carret.market.application.member.dto;

import lombok.Getter;

@Getter
public class SubscriptResponse {

    private final Long itemId;
    private final String name;
    private final String previewUrl;
    private final String location;
    private final int price;
    private final int likeCount;

    public SubscriptResponse(Long itemId, String name, String previewUrl, String location, int price, int likeCount) {
        this.itemId = itemId;
        this.name = name;
        this.previewUrl = previewUrl;
        this.location = location;
        this.price = price;
        this.likeCount = likeCount;
    }

}
