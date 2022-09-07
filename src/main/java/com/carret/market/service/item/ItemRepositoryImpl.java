package com.carret.market.service.item;

import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.like.QLikes.likes;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.domain.item.Item;
import com.carret.market.domain.like.Likes;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemListDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemListDto> findByItemListPaging() {
        // TODO 추후, paging 추가 및 검색 기능 추가(like)
        return jpaQueryFactory.select(
                Projections.fields(ItemListDto.class,
                    item.id.as("itemId"),
                    item.title.as("title"),
                    item.location.as("location"),
                    item.price.as("price"),
                    itemImage.name.as("thumbnail"),
                    likes.count().intValue().as("likeCount")
                ))
            .from(item)
            .innerJoin(itemImage)
                .on(itemImage.item.eq(item)
                    .and(itemImage.thumbnail.isTrue()))
            .fetchJoin()
            .leftJoin(likes)
                .on(likes.item.eq(item))
            .fetchJoin()
            .groupBy(item.id, itemImage.name)
            .fetch();
    }

    @Override
    public Optional<ItemInfoDto> findItemInfoByItemId(Long itemId) {
        ItemInfoDto itemInfoDto = jpaQueryFactory.select(
            Projections.constructor(ItemInfoDto.class,
                    item.id,
                    item.title,
                    item.detail,
                    item.price,
                    item.location,
                    item.viewCount,
                    member.previewUrl,
                    member.nickname,
                    item.category
                ))
            .from(item)
            .innerJoin(member)
                .on(item.member.eq(member))
            .where(item.id.eq(itemId))
            .fetchOne();

        if(!ObjectUtils.isEmpty(itemInfoDto)) {
            itemInfoDto.addImage(jpaQueryFactory.select(itemImage.url)
                .from(itemImage)
                .where(itemImage.item.id.eq(itemId))
                .fetch());

            List<Likes> likesList = jpaQueryFactory.selectFrom(likes)
                .where(likes.item.id.eq(itemId))
                .fetch();

            itemInfoDto.addLikeInfo( likesList.size(),
                likesList.stream()
                    .filter(like -> like.getMember()
                        .getNickname()
                        .equals(itemInfoDto.getNickname()))
                    .findFirst()
                    .orElse(null) != null
            );
        }

        return Optional.ofNullable(itemInfoDto);
    }

    @Override
    public Optional<Likes> findLikesByItemIdAndMemberId(Long memberId, Long itemId) {
        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(likes)
            .where(likes.item.id.eq(itemId)
                .and(likes.member.id.eq(memberId)))
            .fetchOne());
    }
}
