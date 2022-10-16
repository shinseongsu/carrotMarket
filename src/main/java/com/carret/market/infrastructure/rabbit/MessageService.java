package com.carret.market.infrastructure.rabbit;

import com.carret.market.domain.chat.Message;
import com.carret.market.domain.chat.MessageRepository;
import com.carret.market.domain.chat.Room;
import com.carret.market.domain.chat.RoomRepository;
import com.carret.market.domain.member.Member;
import com.carret.market.domain.member.MemberRepository;
import com.carret.market.global.exception.MemberNotFoundException;
import com.carret.market.web.chat.dto.ChatDetail;
import com.carret.market.web.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private static final String CHAT_EXCHANGE_NAME = "amq.topic";

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final RabbitTemplate template;

    @Async
    @EventListener
    public void saveMessage(final ChatDto chatDto) {
        Member member = memberRepository.findById(chatDto.getMemberId())
            .orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

        Room room = roomRepository.findById(chatDto.getRoomId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));

        messageRepository.save(new Message(chatDto.getMessage(), room, member));
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatDto.getRoomId(), ChatDetail.of(chatDto, member));
    }

}
