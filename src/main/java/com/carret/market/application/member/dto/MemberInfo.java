package com.carret.market.application.member.dto;

import com.carret.market.domain.member.Member;
import com.carret.market.infrastructure.file.ImageCode;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfo {

    private String email;
    private String name;
    private String nickname;
    private String previewUrl;
    private String location;

    @Builder
    public MemberInfo(String email, String name, String nickname, String previewUrl, String location) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.previewUrl = previewUrl;
        this.location = location;
    }

    public static MemberInfo of(Member member) {
        return MemberInfo.builder()
            .email(member.getEmail())
            .name(member.getName())
            .nickname(member.getNickname())
            .previewUrl(ImageCode.from(member.getPreviewUrl()))
            .location(member.getGeolocation())
            .build();
    }

}
