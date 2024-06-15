package com.trade.aquariux.service;

import com.trade.aquariux.dto.PriceDTO;
import com.trade.aquariux.entity.Price;

import java.math.BigDecimal;
import java.util.List;

public interface PricesService {

    Price inserOrUpdateBTCETH(String tickerSymbol, BigDecimal bidPrice, BigDecimal askPrice);
    PriceDTO getBestPrice(String tickerSymbol);
}
