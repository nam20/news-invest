package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PaginationMeta;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PaginationMetaMapper {

    @Mapping(source = "number", target = "currentPage")
    @Mapping(source = "size", target = "pageSize")
    PaginationMeta toPaginationMeta(Page<?> page);
}
