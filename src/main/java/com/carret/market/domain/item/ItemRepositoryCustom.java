package com.carret.market.domain.item;

import com.carret.market.domain.like.Likes;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemListDto;
import com.carret.market.web.item.dto.ItemRequest;
import com.carret.market.web.item.dto.ItemRequestDto;
import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {

    List<ItemListDto> findByItemListPaging(ItemRequest itemRequest);

    Optional<ItemInfoDto> findItemInfoByItemId(Long itemId, Long memberId);

    Optional<Likes> findLikesByItemIdAndMemberId(Long memberId, Long itemId);

    Optional<ItemRequestDto> findEditItemByItemId(Long itemId);
}
