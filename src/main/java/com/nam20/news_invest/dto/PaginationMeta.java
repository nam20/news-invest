package com.nam20.news_invest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaginationMeta {
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
}
