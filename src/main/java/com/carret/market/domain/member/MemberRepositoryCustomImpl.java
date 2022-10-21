package com.carret.market.domain.member;

import static com.carret.market.domain.chat.QRoom.room;
import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.application.member.dto.MemberPointResponse;
import com.carret.market.application.member.dto.MyItemInfo;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyItemInfo> findMyItemInfoByMemberId(Long memberId) {
        return queryFactory.select(Projections.constructor(
                MyItemInfo.class,
                item.id,
                itemImage.url,
                item.title,
                item.location,
                item.status,
                item.price,
                ExpressionUtils.as(
                    JPAExpressions
                        .select(room.count())
                        .from(room)
                        .where(room.item.eq(item)), "count"
                )
            ))
            .from(itemImage)
            .innerJoin(itemImage.item, item)
            .innerJoin(item.member, member)
            .where(
                itemImage.thumbnail.isTrue(),
                member.id.eq(memberId)
            )
            .fetch();
    }

    @Override
    public MemberPointResponse findPointByMemberId(Long memberId) {
        return queryFactory.select(Projections.constructor(
                MemberPointResponse.class,
                member.point
            ))
            .from(member)
            .where(member.id.eq(memberId))
            .fetchOne();
    }
}
