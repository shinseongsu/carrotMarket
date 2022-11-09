package com.carret.market.application.chat;

import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_ITEM;
import static com.carret.market.global.exception.ErrorCode.NOT_FOUND_MEMBER;

import com.carret.market.domain.chat.Message;
import com.carret.market.domain.chat.MessageRepository;
import com.carret.market.domain.chat.MessageStatus;
import com.carret.market.domain.chat.Room;
import com.carret.market.domain.chat.RoomRepository;
import com.carret.market.domain.item.Item;
import com.carret.market.domain.item.ItemRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.global.exception.ItemNotFoundException;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.application.chat.dto.ChatResponse;
import com.carret.market.application.member.dto.RoomResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private static final String ENTER_MESSAGE = "입장하였습니다.";

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public ChatResponse enterRoom(Long itemId, String email) {
        Room room = roomRepository.findByItemIdAndEmail(itemId, email)
            .orElseGet(() -> this.createRoom(itemId, email));

        return messageRepository.findByRoomAndEmail(room, email);
    }

    private Room createRoom(Long itemId, String email) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberNotFoundException(NOT_FOUND_MEMBER));

        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException(NOT_FOUND_ITEM));

        Room room = new Room(item, member);

        messageRepository.save(new Message(member.getNickname() + ENTER_MESSAGE, room, member, MessageStatus.NOTICE));
        return roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findByRoomList(Long memberId) {
        return roomRepository.findByRoomList(memberId);
    }

    @Transactional(readOnly = true)
    public ChatResponse findByChattingList(Long roomId, Long memberId) {
        return messageRepository.findByRoomIdAndMemberId(roomId, memberId);
    }

}
