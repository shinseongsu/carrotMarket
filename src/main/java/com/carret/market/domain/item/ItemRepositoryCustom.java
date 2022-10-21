package com.carret.market.domain.item;

import com.carret.market.domain.like.Likes;
import com.carret.market.application.item.dto.ItemInfo;
import com.carret.market.application.item.dto.ItemList;
import com.carret.market.application.item.dto.ItemPagingRequest;
import com.carret.market.application.item.dto.ItemRequest;
import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {

    List<ItemList> findByItemListPaging(ItemPagingRequest itemRequest);

    Optional<ItemInfo> findItemInfoByItemId(Long itemId, Long memberId);

    Optional<Likes> findLikesByItemIdAndMemberId(Long memberId, Long itemId);

    Optional<ItemRequest> findEditItemByItemId(Long itemId);
}
