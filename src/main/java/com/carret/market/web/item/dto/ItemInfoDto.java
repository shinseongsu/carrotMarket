package com.carret.market.web.item.dto;

import com.carret.market.domain.item.Category;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ItemInfoDto {
    private Long itemId;
    private String title;
    private String detail;
    private Integer price;
    private String location;
    private List<String> imageUrlList;
    private Long likeCount;
    private Integer viewCount;
    private String previewUrl;
    private String nickname;
    private String category;

    public ItemInfoDto(Long itemId, String title, String detail, Integer price,
        String location, Long likeCount, Integer viewCount,
        String previewUrl, String nickname, Category category) {
        this.itemId = itemId;
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.location = location;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.previewUrl = Objects.isNull(previewUrl) ? "/images/common/profile.png" : "/images/" + previewUrl;
        this.nickname = nickname;
        this.category = category.getValue();
    }

    public void addImage(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
}
