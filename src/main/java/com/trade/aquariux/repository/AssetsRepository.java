package com.trade.aquariux.repository;

import com.trade.aquariux.Embeddable.AssetsCompositeKey;
import com.trade.aquariux.entity.Assets;
import com.trade.aquariux.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AssetsRepository extends JpaRepository<Assets, AssetsCompositeKey> {
    Optional<Assets> findByAssetsCompositeKeyUserIdAndAssetsCompositeKeyTickerSymbol(String userid, String tickerSymbol);

    List<Assets> findByAssetsCompositeKeyUserId(String userId);
}
