package com.carret.market.domain.item;

import static com.carret.market.domain.item.QItemImage.itemImage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemImageRepositoryCustomImpl implements ItemImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteItemImageByUrl(List<Long> deleteUrls) {
        jpaQueryFactory.delete(itemImage)
            .where(itemImage.id.in(deleteUrls))
            .execute();
    }

    @Override
    public List<ItemImage> findByItem(Item item) {
        return jpaQueryFactory.selectFrom(itemImage)
            .where(itemImage.item.eq(item))
            .fetch();
    }
}
