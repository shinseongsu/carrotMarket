package com.carret.market.web.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter @Getter
@NoArgsConstructor
public class MemberChangeDto {
    private String nickname;
    private String location;
    private MultipartFile previewUrl;

    public MemberChangeDto(String nickname, String location,
        MultipartFile previewUrl) {
        this.nickname = nickname;
        this.location = location;
        this.previewUrl = previewUrl;
    }
}
