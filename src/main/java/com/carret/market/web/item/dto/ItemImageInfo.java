package com.carret.market.web.item.dto;

import lombok.Getter;

@Getter
public class ItemImageInfo {

    private Long itemImageId;
    private String url;

    public ItemImageInfo(Long itemImageId, String url) {
        this.itemImageId = itemImageId;
        this.url = url;
    }
}
