package com.carret.market.domain.item;

import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.like.QLikes.likes;
import static com.carret.market.domain.member.QMember.member;

import com.carret.market.domain.like.Likes;
import com.carret.market.web.item.dto.ItemImageInfo;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemListDto;
import com.carret.market.web.item.dto.ItemRequest;
import com.carret.market.web.item.dto.ItemRequestDto;
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
    public List<ItemListDto> findByItemListPaging(ItemRequest itemRequest) {
        return jpaQueryFactory.select(
                Projections.fields(ItemListDto.class,
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
                searchContains(itemRequest.getSearch())
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
    public Optional<ItemInfoDto> findItemInfoByItemId(Long itemId, Long memberId) {
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
    public Optional<ItemRequestDto> findEditItemByItemId(Long itemId) {
        ItemRequestDto itemRequestDto = jpaQueryFactory.select(
                Projections.constructor(ItemRequestDto.class,
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
}
