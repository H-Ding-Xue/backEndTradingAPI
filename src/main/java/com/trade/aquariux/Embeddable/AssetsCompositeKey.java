package com.trade.aquariux.Embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable@Data@NoArgsConstructor
public class AssetsCompositeKey {
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "ticker_symbol", nullable = false)
    private String tickerSymbol;
}
