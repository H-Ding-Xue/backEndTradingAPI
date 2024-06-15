package com.trade.aquariux.service;

import com.trade.aquariux.dto.PriceDTO;
import com.trade.aquariux.entity.Price;
import com.trade.aquariux.exception.TickerNotFoundException;
import com.trade.aquariux.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.trade.aquariux.constants.Constants.*;

@Service
public class PricesServiceImpl implements PricesService {
    private final PriceRepository priceRepository;
    @Autowired
    public PricesServiceImpl(final PriceRepository priceRepository){
        this.priceRepository = priceRepository;
    }

    @Override
    @Transactional
    public Price inserOrUpdateBTCETH(String tickerSymbol, BigDecimal bidPrice, BigDecimal askPrice){
        Price price = new Price(tickerSymbol,bidPrice,askPrice,LocalDateTime.now(),SYSTEM,LocalDateTime.now(),SYSTEM);
        return priceRepository.save(price);
    }

    @Override
    @Transactional
    public PriceDTO getBestPrice(String tickerSymbol) {
        Price price = priceRepository.findByTickerSymbol(tickerSymbol).orElseThrow(()->new TickerNotFoundException(tickerSymbol+" cannot be found in the database"));
        return new PriceDTO(price.getTickerSymbol(), price.getBidPrice(),price.getAskPrice());
    }
}
