package com.carret.market.web.member.dto;

import com.carret.market.domain.item.ItemStatus;
import lombok.Getter;

@Getter
public class MyItemInfo {
    private final Long itemId;
    private final String imageUrl;
    private final String title;
    private final String location;
    private final ItemStatus status;
    private final int price;
    private final Long count;

    public MyItemInfo(Long itemId, String imageUrl, String title, String location,
        ItemStatus status, int price, Long count) {
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.title = title;
        this.location = location;
        this.status = status;
        this.price = price;
        this.count = count;
    }

}
