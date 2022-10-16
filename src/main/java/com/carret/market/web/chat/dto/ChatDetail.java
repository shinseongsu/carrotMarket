package com.carret.market.web.chat.dto;

import com.carret.market.domain.chat.MessageStatus;
import com.carret.market.domain.member.Member;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatDetail {

    private final Long memberId;
    private final String message;
    private final String nickName;
    private final String profile;
    private final MessageStatus status;

    @Builder
    public ChatDetail(Long memberId, String message, String nickName, String profile, MessageStatus status) {
        this.memberId = memberId;
        this.message = message;
        this.nickName = nickName;
        this.profile = profile;
        this.status = status;
    }

    public static ChatDetail of(ChatDto chatDto, Member member) {
        return ChatDetail.builder()
            .memberId(member.getId())
            .message(chatDto.getMessage())
            .nickName(member.getNickname())
            .profile(Objects.isNull(member.getPreviewUrl()) ? "/images/common/profile.png" : member.getPreviewUrl())
            .status(MessageStatus.MESSAGE)
            .build();
    }

}
