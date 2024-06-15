package com.trade.aquariux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data@AllArgsConstructor
public class PriceDTO {

    private String tickerSymbol;

    private BigDecimal bidPrice;

    private BigDecimal askPrice;
}
