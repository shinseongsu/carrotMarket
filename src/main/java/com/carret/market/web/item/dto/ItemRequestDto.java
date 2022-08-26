package com.carret.market.web.item.dto;

import com.carret.market.domain.item.Category;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ItemRequestDto {

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    private List<MultipartFile> imageUrl;

    @NotNull(message = "카테고리는 필수 입력값입니다.")
    private Category category;

    @NotNull(message = "가격은 필수 입력값입니다.")
    private Integer price;

    @NotBlank(message = "설명글은 필수 입력값입니다.")
    private String description;

    public ItemRequestDto(String title,
        List<MultipartFile> imageUrl, Category category, Integer price, String description) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
