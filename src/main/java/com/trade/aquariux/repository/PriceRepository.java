package com.trade.aquariux.repository;

import com.trade.aquariux.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price,String> {
    Optional<Price> findByTickerSymbol(String tickerSymbol);
}
