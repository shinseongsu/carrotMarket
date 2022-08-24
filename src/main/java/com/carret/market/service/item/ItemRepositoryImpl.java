package com.carret.market.service.item;

import static com.carret.market.domain.item.QItem.item;
import static com.carret.market.domain.item.QItemImage.itemImage;
import static com.carret.market.domain.like.QLikes.likes;

import com.carret.market.web.item.dto.ItemListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemListDto> findByItemListPaging() {
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
                .on(itemImage.item.eq(item).and(itemImage.thumbnail.isTrue()))
            .leftJoin(likes)
                .on(likes.item.eq(item))
            .fetchJoin()
            .groupBy(item.id, itemImage.name)
            .fetch();
    }
}
