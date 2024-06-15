package com.trade.aquariux.service;

import com.trade.aquariux.dto.HistoricRecordDTO;
import com.trade.aquariux.dto.TradeDTO;
import com.trade.aquariux.entity.TradeHistory;
import com.trade.aquariux.exception.TickerNotFoundException;
import com.trade.aquariux.exception.UserNotFoundException;
import com.trade.aquariux.repository.AssetsRepository;
import com.trade.aquariux.repository.HistoricTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoricTradeServiceImpl implements HistoricTradeService{

    private final HistoricTradeRepository historicTradeRepository;
    @Autowired
    public HistoricTradeServiceImpl(final HistoricTradeRepository historicTradeRepository){
        this.historicTradeRepository = historicTradeRepository;
    }

    @Override
    @Transactional
    public void historicRecord(TradeDTO tradeDTO, BigDecimal averagePrice) {
        TradeHistory tradeHistory = new TradeHistory();
        tradeHistory.setTradedAt(LocalDateTime.now());
        tradeHistory.setTradeSize(tradeDTO.getTradeSize());
        tradeHistory.setTradeType(tradeDTO.getTradeType());
        tradeHistory.setTradedBy(tradeDTO.getTradedBy());
        tradeHistory.setTickerSymbol(tradeDTO.getTickerSymbol());
        tradeHistory.setTradeAveragePrice(averagePrice);
        historicTradeRepository.save(tradeHistory);
    }

    @Override
    @Transactional
    public List<HistoricRecordDTO> getAllHistoricRecords(String userId) {
        List<HistoricRecordDTO> results = new ArrayList<>();
        List<TradeHistory> records = historicTradeRepository.findByTradedBy(userId);
        if(records.isEmpty())
            throw new UserNotFoundException(userId+ " is not found in records table");
        for(TradeHistory i : records) {
            results.add(convertEntityToDto(i));
        }
        return results;
    }

    private HistoricRecordDTO convertEntityToDto(TradeHistory tradeHistory){
        return new HistoricRecordDTO(tradeHistory.getTradeType(),tradeHistory.getTickerSymbol(),tradeHistory.getTradeSize(),tradeHistory.getTradeAveragePrice(),tradeHistory.getTradedAt());
    }
}
