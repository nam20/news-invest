package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.PaginationMeta;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationMetaMapper {

    private final ModelMapper modelMapper;

    public PaginationMetaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Page<?>, PaginationMeta>() {
            @Override
            protected void configure() {
                map().setTotalPages(source.getTotalPages());
                map().setTotalElements(source.getTotalElements());
                map().setCurrentPage(source.getNumber());
                map().setPageSize(source.getSize());
            }
        });
    }

    public PaginationMeta toPaginationMeta(Page<?> page) {
        return modelMapper.map(page, PaginationMeta.class);
    }
}
