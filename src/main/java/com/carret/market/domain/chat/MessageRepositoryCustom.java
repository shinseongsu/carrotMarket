package com.carret.market.domain.chat;

import com.carret.market.application.chat.dto.ChatResponse;

public interface MessageRepositoryCustom {

    ChatResponse findByRoomAndEmail(Room enterRoom, String email);

    ChatResponse findByRoomIdAndMemberId(Long roomId, Long memberId);
}
