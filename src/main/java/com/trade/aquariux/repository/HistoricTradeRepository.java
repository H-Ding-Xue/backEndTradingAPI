package com.trade.aquariux.repository;

import com.trade.aquariux.Embeddable.AssetsCompositeKey;
import com.trade.aquariux.entity.Assets;
import com.trade.aquariux.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoricTradeRepository extends JpaRepository<TradeHistory, Long> {
    List<TradeHistory> findByTradedBy(String lastname);
}
