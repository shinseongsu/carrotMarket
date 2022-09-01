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
    private int likeCount;
    private Integer viewCount;
    private String previewUrl;
    private String nickname;
    private String category;
    private boolean isOwnerLikes;

    public ItemInfoDto(Long itemId, String title, String detail, Integer price,
        String location, Integer viewCount,
        String previewUrl, String nickname, Category category) {
        this.itemId = itemId;
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.location = location;
        this.viewCount = viewCount;
        this.previewUrl = Objects.isNull(previewUrl) ? "/images/common/profile.png" : "/images/" + previewUrl;
        this.nickname = nickname;
        this.category = category.getValue();
    }

    public void addImage(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public void addLikeInfo(int likeCount, boolean isOwnerLikes) {
        this.likeCount = likeCount;
        this.isOwnerLikes = isOwnerLikes;
    }

}
