package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.UserResponse;
import com.nam20.news_invest.entity.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user);
}
