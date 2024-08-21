package com.nam20.news_invest.mapper;

import com.nam20.news_invest.dto.DailyMarketPriceResponse;
import com.nam20.news_invest.entity.DailyCoinMetric;
import com.nam20.news_invest.entity.DailyStockMetric;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class DailyMarketPriceMapper {

    private final ModelMapper modelMapper;

    public DailyMarketPriceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<DailyStockMetric, DailyMarketPriceResponse>() {
            @Override
            protected void configure() {
                map().setPrice(source.getClosePrice());
            }
        });
    }

    public DailyMarketPriceResponse stockToDto(DailyStockMetric dailyStockMetric) {
        return modelMapper.map(dailyStockMetric, DailyMarketPriceResponse.class);
    }

    public DailyMarketPriceResponse coinToDto(DailyCoinMetric dailyCoinMetric) {
        return modelMapper.map(dailyCoinMetric, DailyMarketPriceResponse.class);
    }
}
