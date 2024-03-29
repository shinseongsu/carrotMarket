package com.carret.market.domain.chat;

import static com.carret.market.domain.chat.QMessage.message1;
import static com.carret.market.domain.chat.QRoom.room;
import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.application.chat.dto.ChatInfo;
import com.carret.market.application.chat.dto.ChatResponse;
import com.carret.market.domain.member.Member;
import com.querydsl.core.types.Projections;
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
                member.email.eq(email),
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

        Member own = queryFactory.selectFrom(room)
                .innerJoin(room.item, item)
                .innerJoin(item.member, member)
                .where(room.eq(enterRoom))
                .fetchOne()
                .getItem()
                .getMember();

        return new ChatResponse(enterRoom.getId(), own.getEmail().equals(email), chatInfos);
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

        Member own = queryFactory.selectFrom(room)
                .innerJoin(room.item, item)
                .innerJoin(item.member, member)
                .where(room.id.eq(roomId))
                .fetchOne()
                .getItem()
                .getMember();

        return new ChatResponse(roomId, own.getId().equals(memberId), chatInfos);
    }
}
