package com.carret.market.infrastructure.file;

import java.util.Objects;
import lombok.Getter;

@Getter
public enum ImageCode {

    BASIC_PROFILE_IMAGE("/images/common/profile.png");

    private final String value;

    ImageCode(String value) {
        this.value = value;
    }

    public static String from(String image) {
        if(Objects.isNull(image)) {
            return BASIC_PROFILE_IMAGE.getValue();
        }

        return image;
    }

}
