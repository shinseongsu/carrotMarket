package com.carret.market.web.chat.dto;

import com.carret.market.domain.chat.MessageStatus;
import java.util.Objects;
import lombok.Getter;

@Getter
public class ChatInfo {
    private final String message;
    private final String nickName;
    private final String profile;
    private final boolean own;
    private final MessageStatus status;

    public ChatInfo(String message, String nickName, String profile, boolean own,
        MessageStatus status) {
        this.message = message;
        this.nickName = nickName;
        this.profile = Objects.isNull(profile) ? "/images/common/profile.png" : profile;
        this.own = own;
        this.status = status;
    }
}
