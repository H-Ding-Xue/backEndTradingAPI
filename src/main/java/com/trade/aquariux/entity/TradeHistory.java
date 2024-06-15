package com.trade.aquariux.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class TradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "native")
    @Column(name = "trade_id", updatable = false)
    private Long tradeId;

    @Column(name = "trade_type", updatable = false)
    private String tradeType;

    @Column(name = "ticker_symbol", updatable = false)
    private String tickerSymbol;

    @Column(name = "trade_quantity", updatable = false, scale = 10, precision = 20)
    private BigDecimal tradeSize;

    @Column(name = "average_price", updatable = false, scale = 10, precision = 20)
    private BigDecimal tradeAveragePrice;

    @Column(name = "traded_at", updatable = false)
    private LocalDateTime tradedAt;

    @Column(name = "traded_by", updatable = false)
    private String tradedBy;
}
