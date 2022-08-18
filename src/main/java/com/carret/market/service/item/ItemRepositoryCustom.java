package com.carret.market.service.item;

import com.carret.market.web.item.dto.ItemListDto;
import java.util.List;

public interface ItemRepositoryCustom {

    List<ItemListDto> findByItemListPaging();

}
