package com.carret.market.application.member.dto;

import lombok.Getter;

@Getter
public class RoomResponse {

    private final Long roomId;
    private final Long itemId;
    private final String imageUrl;
    private final String nickname;
    private final String message;

    public RoomResponse(Long roomId, Long itemId, String imageUrl, String nickname, String message) {
        this.roomId = roomId;
        this.itemId = itemId;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.message = message;
    }

}
