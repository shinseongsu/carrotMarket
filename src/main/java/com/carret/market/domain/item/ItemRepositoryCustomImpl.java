package com.carret.market.domain.item;

import static com.carret.market.domain.chat.QRoom.room;
import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.like.QLikes.likes;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.domain.like.Likes;
import com.carret.market.application.item.dto.ItemImageInfo;
import com.carret.market.application.item.dto.ItemInfo;
import com.carret.market.application.item.dto.ItemList;
import com.carret.market.application.item.dto.ItemPagingRequest;
import com.carret.market.application.item.dto.ItemRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemList> findByItemListPaging(ItemPagingRequest itemRequest) {
        return jpaQueryFactory.select(
                Projections.fields(ItemList.class,
                    item.id.as("itemId"),
                    item.title.as("title"),
                    item.location.as("location"),
                    item.price.as("price"),
                    itemImage.url.as("thumbnail"),
                    likes.count().intValue().as("likeCount")
                ))
            .from(itemImage)
            .innerJoin(itemImage.item, item)
            .leftJoin(likes).on(likes.item.eq(item))
            .fetchJoin()
            .where(
                itemImage.thumbnail.isTrue(),
                searchContains(itemRequest.getSearch()),
                item.status.eq(ItemStatus.SELL)
            )
            .groupBy(item.id, itemImage.url)
            .offset(itemRequest.getOffset() * itemRequest.getSize())
            .limit(itemRequest.getSize())
            .orderBy(item.createdBy.desc())
            .fetch();
    }

    private BooleanExpression searchContains(String searchBar) {
        if (Objects.isNull(searchBar)) {
            return null;
        }

        return item.title.contains(searchBar)
            .or(item.location.contains(searchBar));
    }

    @Override
    public Optional<ItemInfo> findItemInfoByItemId(Long itemId, Long memberId) {
        ItemInfo itemInfoDto = jpaQueryFactory.select(
                Projections.constructor(ItemInfo.class,
                    item.id,
                    item.title,
                    item.detail,
                    item.price,
                    item.location,
                    item.viewCount,
                    member.previewUrl,
                    member.nickname,
                    item.category,
                    member.id.eq(memberId)
                ))
            .from(item)
            .innerJoin(item.member, member)
            .where(item.id.eq(itemId))
            .fetchOne();

        if (!ObjectUtils.isEmpty(itemInfoDto)) {
            itemInfoDto.addImage(jpaQueryFactory.select(itemImage.url)
                .from(itemImage)
                .where(itemImage.item.id.eq(itemId))
                .fetch());

            List<Likes> likesList = jpaQueryFactory.selectFrom(likes)
                .where(likes.item.id.eq(itemId))
                .fetch();

            itemInfoDto.addLikeInfo(likesList.size(),
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
            .where(
                likes.item.id.eq(itemId),
                likes.member.id.eq(memberId))
            .fetchOne());
    }

    @Override
    public Optional<ItemRequest> findEditItemByItemId(Long itemId) {
        ItemRequest itemRequestDto = jpaQueryFactory.select(
                Projections.constructor(ItemRequest.class,
                    item.title,
                    item.id,
                    item.category,
                    item.price,
                    item.detail
                ))
            .from(item)
            .where(item.id.eq(itemId))
            .fetchOne();

        List<ItemImageInfo> originalImageUrl = jpaQueryFactory
            .select(Projections.constructor(
                ItemImageInfo.class,
                itemImage.id,
                itemImage.url
            ))
            .from(itemImage)
            .innerJoin(itemImage.item, item)
            .where(item.id.eq(itemId))
            .fetch();

        itemRequestDto.addOriginalImageUrl(originalImageUrl);
        return Optional.ofNullable(itemRequestDto);
    }

    @Override
    public Optional<Item> findItemByRoomId(Long roomId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(room)
                    .innerJoin(room.item, item)
                    .where(room.id.eq(roomId))
                    .fetchOne()
                    .getItem());
    }
}
