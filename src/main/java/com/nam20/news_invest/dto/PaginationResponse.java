package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaginationResponse<T> {
    private List<T> content;
    private PaginationMeta pagination;

    public PaginationResponse(List<T> content, PaginationMeta pagination) {
        this.content = content;
        this.pagination = pagination;
    }
}
