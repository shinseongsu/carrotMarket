package com.carret.market.domain.chat;

import static com.carret.market.domain.chat.QMessage.message1;
import static com.carret.market.domain.chat.QRoom.room;
import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.web.chat.dto.ChatInfo;
import com.carret.market.web.chat.dto.ChatResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public ChatResponse findByRoomAndEmail(Room enterRoom, String email) {
        List<ChatInfo> chatInfos = queryFactory
            .select(Projections.constructor(
                ChatInfo.class,
                message1.message,
                member.nickname,
                member.previewUrl,
                item.member.email.eq(email),
                message1.messageStatus
            ))
            .from(message1)
            .innerJoin(message1.room, room)
            .innerJoin(message1.member, member)
            .innerJoin(room.item, item)
            .where(
                room.eq(enterRoom)
            )
            .orderBy(message1.sendDate.desc())
            .fetch();

        return new ChatResponse(enterRoom.getId(), chatInfos);
    }

    @Override
    public ChatResponse findByRoomIdAndMemberId(Long roomId, Long memberId) {
        List<ChatInfo> chatInfos = queryFactory
            .select(Projections.constructor(
                ChatInfo.class,
                message1.message,
                member.nickname,
                member.previewUrl,
                member.id.eq(memberId),
                message1.messageStatus
            ))
            .from(message1)
            .innerJoin(message1.room, room)
            .innerJoin(message1.member, member)
            .innerJoin(room.item, item)
            .where(
                room.id.eq(roomId)
            )
            .orderBy(message1.sendDate.desc())
            .fetch();

        return new ChatResponse(roomId, chatInfos);
    }
}
