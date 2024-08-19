package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyCoinMarketData;
import com.nam20.news_invest.entity.DailyStockPrice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyMarketPriceMapper {

    private final ModelMapper modelMapper;

    public DailyMarketPriceResponse stockToDto(DailyStockPrice dailyStockPrice) {
        Converter<DailyStockPrice, DailyMarketPriceResponse> stockToDtoConverter = context -> {
            DailyStockPrice source = context.getSource();
            return new DailyMarketPriceResponse(
                    source.getDate(),
                    source.getClosePrice()
            );
        };

        modelMapper.addConverter(stockToDtoConverter);
        return modelMapper.map(dailyStockPrice, DailyMarketPriceResponse.class);
    }

    public DailyMarketPriceResponse coinToDto(DailyCoinMarketData dailyCoinMarketData) {
        return modelMapper.map(dailyCoinMarketData, DailyMarketPriceResponse.class);
    }
}
