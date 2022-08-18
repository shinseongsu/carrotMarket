package com.carret.market.web.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemListDto {

    private Long itemId;
    private String title;
    private String location;
    private Integer price;
    private String thumbnail;
    private Integer likeCount;

    @Builder
    public ItemListDto(Long itemId, String title, String location, Integer price,
        String thumbnail, Integer likeCount) {
        this.itemId = itemId;
        this.title = title;
        this.location = location;
        this.price = price;
        this.thumbnail = thumbnail;
        this.likeCount = likeCount;
    }
}
