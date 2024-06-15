package com.trade.aquariux.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data@AllArgsConstructor@NoArgsConstructor
public class AssetsDTO {

    private String userId;

    private String tickerSymbol;

    private BigDecimal value;
}
