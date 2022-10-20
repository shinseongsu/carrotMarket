package com.carret.market.domain.item;

import java.util.List;

public interface ItemImageRepositoryCustom {

    void deleteItemImageByUrl(List<Long> deleteUrls);

    List<ItemImage> findByItem(Item item);

}
