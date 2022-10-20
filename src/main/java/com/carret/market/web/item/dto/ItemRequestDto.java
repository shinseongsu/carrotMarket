package com.carret.market.web.item.dto;

import com.carret.market.domain.item.Category;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemRequestDto {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    private List<ItemImageInfo> originalImageUrl;

    private List<Long> deleteImageIds;

    private Long itemId;

    private List<MultipartFile> imageUrl;

    private String categoryName;

    @NotNull(message = "카테고리는 필수 입력값입니다.")
    private Category category;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private Integer price;

    @NotBlank(message = "설명글은 필수 입력값입니다.")
    private String description;

    @Builder
    public ItemRequestDto(String title, Long itemId, Category category, Integer price, String description) {
        this.title = title;
        this.itemId = itemId;
        this.category = category;
        this.categoryName = category.getValue();
        this.price = price;
        this.description = description;
    }

    public void addOriginalImageUrl(List<ItemImageInfo> originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

}
