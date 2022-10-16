package com.carret.market.domain.like;

import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.like.QLikes.likes;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.web.member.dto.SubscriptResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikesRepositoryCustomImpl implements LikesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SubscriptResponse> findBySubscripts(Long memberId) {
        return queryFactory.select(Projections.constructor(SubscriptResponse.class,
                item.id,
                item.title,
                itemImage.url,
                item.location,
                item.price,
                ExpressionUtils.as(
                    JPAExpressions
                        .select(likes.count().intValue())
                        .from(likes)
                        .where(likes.item.eq(item)), "ExpressionUtils")
            ))
            .from(itemImage)
            .innerJoin(itemImage.item, item)
            .innerJoin(item.member, member)
            .where(
                member.id.eq(memberId),
                itemImage.thumbnail.isTrue())
            .fetch();
    }
}
