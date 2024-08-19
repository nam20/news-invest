package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyCoinMarketData;
import com.nam20.news_invest.entity.DailyStockPrice;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class DailyMarketPriceMapper {

    private final ModelMapper modelMapper;

    public DailyMarketPriceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<DailyStockPrice, DailyMarketPriceResponse>() {
            @Override
            protected void configure() {
                map().setPrice(source.getClosePrice());
            }
        });
    }

    public DailyMarketPriceResponse stockToDto(DailyStockPrice dailyStockPrice) {
        return modelMapper.map(dailyStockPrice, DailyMarketPriceResponse.class);
    }

    public DailyMarketPriceResponse coinToDto(DailyCoinMarketData dailyCoinMarketData) {
        return modelMapper.map(dailyCoinMarketData, DailyMarketPriceResponse.class);
    }
}
