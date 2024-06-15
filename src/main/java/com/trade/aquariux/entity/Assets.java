package com.trade.aquariux.entity;

import com.trade.aquariux.Embeddable.AssetsCompositeKey;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Assets {
    @EmbeddedId
    private AssetsCompositeKey assetsCompositeKey;

    @Column(name = "ticker_quantity", scale = 10, precision = 20)
    private BigDecimal tickerQuantity;

}
