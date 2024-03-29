package com.carret.market.domain.chat;

import com.carret.market.application.chat.dto.PayFormResponse;
import com.carret.market.application.member.dto.RoomResponse;
import java.util.List;
import java.util.Optional;

public interface RoomRepositoryCustom {

    Optional<Room> findByItemIdAndEmail(Long itemId, String email);

    List<RoomResponse> findByRoomList(Long memberId);

    PayFormResponse findSellerIdAndAmountByRoomId(Long roomId);
}
