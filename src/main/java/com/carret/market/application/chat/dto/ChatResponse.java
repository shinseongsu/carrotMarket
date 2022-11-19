package com.carret.market.application.chat.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ChatResponse {

    private final Long roomId;
    private final boolean ownRoom;
    private final List<ChatInfo> chatInfos;

    public ChatResponse(Long roomId, boolean ownRoom, List<ChatInfo> chatInfos) {
        this.roomId = roomId;
        this.ownRoom = ownRoom;
        this.chatInfos = chatInfos;
    }
}
