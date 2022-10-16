package com.carret.market.web.chat.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatDto {

    private String message;
    private Long memberId;
    private Long roomId;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime regDate = LocalDateTime.now();

    public ChatDto(String message, Long memberId, Long roomId) {
        this.message = message;
        this.memberId = memberId;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ChatDto{" +
            "message='" + message + '\'' +
            ", memberId=" + memberId +
            ", roomId=" + roomId +
            ", regDate=" + regDate +
            '}';
    }
}
