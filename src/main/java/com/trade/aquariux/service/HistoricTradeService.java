package com.trade.aquariux.service;

import com.trade.aquariux.dto.HistoricRecordDTO;
import com.trade.aquariux.dto.TradeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface HistoricTradeService {
    void historicRecord(TradeDTO tradeDTO, BigDecimal averagePrice);

    List<HistoricRecordDTO> getAllHistoricRecords(String userId);
}
