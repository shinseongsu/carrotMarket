package com.carret.market.domain.item;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Category {
    DIGITAL("디지털기기"),
    HOME_APPLICATION("생활가전"),
    FURNITURE("가구/인테리어"),
    LIFE("생활/가전"),
    CHILD("유아동"),
    CHILD_BOOK("유아도서"),
    WOMEN_CLOTHING("여성의류"),
    WOMEN_ACCESORIES("여성잡화"),
    MEN_FASHION("남성패션"),
    BEAUTY("뷰티"),
    SPORTS("스포츠"),
    GAME("게임"),
    BOOK("도서"),
    TICKET("티켓"),
    FOOD("가공식품"),
    PET("반려동물"),
    PLANT("식물"),
    OTHER("기타");

    private String value;

    Category(String value) {
        this.value = value;
    }

    public static List<Map.Entry<String, String>> getCategories() {
        return Arrays.stream(values())
            .map(category -> Map.entry(category.name(), category.value))
            .collect(Collectors.toList());
    }

}
