package com.trade.aquariux.service;

import com.trade.aquariux.dto.AssetsDTO;

import java.util.List;

public interface AssetsService {
    AssetsDTO findBuyingPower(String userId, String tickerSymbol);
    void updateUsdt(AssetsDTO usdt);
    AssetsDTO findEthOrBtc(String userId, String tickerSymbol);
    void addOrUpdateBtcEth(AssetsDTO btcOrEth);

    List<AssetsDTO> findAllAssetsByUser(String userId);
}
