package com.carret.market.web.member.dto;

import com.carret.market.domain.member.Member;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberInfoDto {

    private String email;
    private String name;
    private String nickname;
    private String previewUrl;
    private String location;

    @Builder
    public MemberInfoDto(String email, String name, String nickname, String previewUrl, String location) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.previewUrl = previewUrl;
        this.location = location;
    }

    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
            .email(member.getEmail())
            .name(member.getName())
            .nickname(member.getNickname())
            .previewUrl(Objects.isNull(member.getPreviewUrl()) ? "/images/common/profile.png" : "/images/" + member.getPreviewUrl())
            .location(member.getGeolocation())
            .build();
    }

}
