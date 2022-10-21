package com.carret.market.application.chat.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ChatResponse {

    private final Long roomId;
    private final List<ChatInfo> chatInfos;

    public ChatResponse(Long roomId, List<ChatInfo> chatInfos) {
        this.roomId = roomId;
        this.chatInfos = chatInfos;
    }
}
