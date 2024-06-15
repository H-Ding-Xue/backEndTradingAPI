package com.trade.aquariux.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data@AllArgsConstructor
public class HistoricRecordDTO {
    private String tradeType;
    private String tickerSymbol;
    private BigDecimal tradeSize;
    private BigDecimal tradeAveragePrice;
    private LocalDateTime tradedAt;

}
