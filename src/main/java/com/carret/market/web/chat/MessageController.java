package com.carret.market.web.chat;

import com.carret.market.application.chat.dto.ChatDetail;
import com.carret.market.application.chat.dto.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MessageController {

    private static final String CHAT_QUEUE_NAME = "chat.queue";

    private final ApplicationEventPublisher applicationEventPublisher;

    @MessageMapping("chat.message.{roomId}")
    public void send(MessageRequest messageRequest, @DestinationVariable Long roomId) {
        applicationEventPublisher.publishEvent(messageRequest);
    }

    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDetail chatDetail) {
        // TODO 알림 구현 예정

    }

}
