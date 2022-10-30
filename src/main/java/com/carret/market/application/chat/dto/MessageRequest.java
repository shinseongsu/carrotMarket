package com.carret.market.application.chat.dto;

import com.carret.market.domain.chat.MessageStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageRequest {

    private String message;
    private Long memberId;
    private Long roomId;

    private MessageStatus messageStatus;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime regDate = LocalDateTime.now();

    public MessageRequest(String message, Long memberId, Long roomId) {
        this.message = message;
        this.memberId = memberId;
        this.roomId = roomId;
    }
}
