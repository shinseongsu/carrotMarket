package com.carret.market.service.item;

import com.carret.market.domain.like.Likes;
import com.carret.market.web.item.dto.ItemInfoDto;
import com.carret.market.web.item.dto.ItemListDto;
import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {

    List<ItemListDto> findByItemListPaging();

    Optional<ItemInfoDto> findItemInfoByItemId(Long itemId);

    Optional<Likes> findLikesByItemIdAndMemberId(Long memberId, Long itemId);
}
