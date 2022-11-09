package com.carret.market.application.item.dto;

import com.carret.market.domain.item.Category;
import com.carret.market.infrastructure.file.ImageCode;
import java.util.List;
import lombok.Getter;

@Getter
public class ItemInfo {

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
    private boolean isOwnerItem;

    public ItemInfo(Long itemId, String title, String detail, Integer price,
        String location, Integer viewCount,
        String previewUrl, String nickname, Category category, boolean isOwnerItem) {
        this.itemId = itemId;
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.location = location;
        this.viewCount = viewCount;
        this.previewUrl = ImageCode.from(previewUrl);
        this.nickname = nickname;
        this.category = category.getValue();
        this.isOwnerItem = isOwnerItem;
    }

    public void addImage(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public void addLikeInfo(int likeCount, boolean isOwnerLikes) {
        this.likeCount = likeCount;
        this.isOwnerLikes = isOwnerLikes;
    }

}
