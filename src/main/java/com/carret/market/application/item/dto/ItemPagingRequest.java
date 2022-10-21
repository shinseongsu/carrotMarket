package com.carret.market.application.item.dto;

import lombok.Getter;

@Getter
public class ItemPagingRequest {

    private static final int DEFAULT_OFFSET = 0;
    private static final int DEFAULT_SIZE = 8;

    private int offset;
    private int size;
    private String search;

    public ItemPagingRequest() {
        this.offset = DEFAULT_OFFSET;
        this.size = DEFAULT_SIZE;
    }

    public ItemPagingRequest(String search) {
        this.offset = DEFAULT_OFFSET;
        this.size = DEFAULT_SIZE;
        this.search = search;
    }

}
