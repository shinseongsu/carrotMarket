package com.carret.market.web.item.dto;

import lombok.Getter;

@Getter
public class ItemRequest {

    private int offset;
    private int size;
    private String search;

    public ItemRequest() {
        this.offset = 0;
        this.size = 8;
    }

    public ItemRequest(String search) {
        this.offset = 0;
        this.size = 8;
        this.search = search;
    }

}
