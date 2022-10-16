package com.carret.market.domain.chat;

import static com.carret.market.domain.chat.QMessage.message1;
import static com.carret.market.domain.chat.QRoom.room;
import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.member.QMember.member;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

import com.carret.market.web.member.dto.RoomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Room> findByItemIdAndEmail(Long itemId, String email) {
        return Optional.ofNullable(queryFactory
            .selectFrom(room)
            .innerJoin(room.item, item)
            .on(item.id.eq(itemId))
            .innerJoin(room.buyer, member)
            .on(member.email.eq(email))
            .where(
                eqItemId(itemId),
                eqEmail(email)
            )
            .fetchOne());
    }

    private BooleanExpression eqItemId(Long itemId) {
        return item.id.eq(itemId);
    }

    private BooleanExpression eqEmail(String email) {
        return member.email.eq(email);
    }

    @Override
    public List<RoomResponse> findByRoomList(Long memberId) {

        List<LocalDateTime> sendDateList = queryFactory
            .select(message1.sendDate.max())
            .from(message1)
            .groupBy(message1.room)
            .fetch();

        return queryFactory
            .select(Projections.constructor(
                RoomResponse.class,
                room.id,
                item.id,
                itemImage.url,
                member.nickname,
                message1.message
            ))
            .from(room)
            .innerJoin(room.item, item)
            .innerJoin(room.buyer, member)
            .innerJoin(itemImage)
            .on(item.eq(itemImage.item))
            .innerJoin(message1)
            .on(message1.room.eq(room))
            .where(
                itemImage.thumbnail.isTrue(),
                eqMemberIdOrBuyerId(memberId),
                message1.sendDate.in(sendDateList)
            )
            .fetch();
    }

    private BooleanExpression eqMemberIdOrBuyerId(Long memberId) {
        return room.buyer.id.eq(memberId)
            .or(room.item.id.eq(memberId));
    }

}
