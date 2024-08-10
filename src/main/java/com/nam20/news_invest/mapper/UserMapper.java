package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.UserDto;
import com.nam20.news_invest.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
