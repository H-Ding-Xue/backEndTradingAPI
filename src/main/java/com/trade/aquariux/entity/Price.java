package com.trade.aquariux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Columns;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Getter@Setter@ToString@AllArgsConstructor@NoArgsConstructor
public class Price {
    @Id
    @Column(name = "ticker_symbol")
    private String tickerSymbol;

    @Column(name = "bid_price", scale = 10, precision = 20)
    private BigDecimal bidPrice;

    @Column(name = "ask_price", scale = 10, precision = 20)
    private BigDecimal askPrice;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", insertable = false)
    private String updatedBy;

}
