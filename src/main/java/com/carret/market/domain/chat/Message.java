package com.carret.market.domain.chat;

import com.carret.market.domain.base.BaseTimeEntity;
import com.carret.market.domain.member.Member;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String message;

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @CreatedDate
    private LocalDateTime sendDate;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Member member;

    public Message(String message, Room room, Member member) {
        this.message = message;
        this.room = room;
        this.member = member;
        this.messageStatus = MessageStatus.MESSAGE;
    }

    public Message(String message, Room room, Member member, MessageStatus messageStatus) {
        this.message = message;
        this.room = room;
        this.member = member;
        this.messageStatus = messageStatus;
    }

}
