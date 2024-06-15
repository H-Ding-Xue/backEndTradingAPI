package com.trade.aquariux.service;

import com.trade.aquariux.Embeddable.AssetsCompositeKey;
import com.trade.aquariux.dto.AssetsDTO;
import com.trade.aquariux.dto.HistoricRecordDTO;
import com.trade.aquariux.dto.PriceDTO;
import com.trade.aquariux.entity.Assets;
import com.trade.aquariux.entity.Price;
import com.trade.aquariux.entity.TradeHistory;
import com.trade.aquariux.exception.TickerNotFoundException;
import com.trade.aquariux.exception.UserNotFoundException;
import com.trade.aquariux.repository.AssetsRepository;
import com.trade.aquariux.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetsServiceImpl implements AssetsService{

    private final AssetsRepository assetsRepository;
    @Autowired
    public AssetsServiceImpl(final AssetsRepository assetsRepository){
        this.assetsRepository = assetsRepository;
    }

    @Override
    @Transactional
    public AssetsDTO findBuyingPower(String userId, String tickerSymbol) {
        Assets assets = assetsRepository.findByAssetsCompositeKeyUserIdAndAssetsCompositeKeyTickerSymbol(userId,tickerSymbol).orElseThrow(()->new TickerNotFoundException(tickerSymbol+" for " +userId+ " cannot be found in the database"));
        return new AssetsDTO(assets.getAssetsCompositeKey().getUserId(),assets.getAssetsCompositeKey().getTickerSymbol(),assets.getTickerQuantity());
    }

    @Override
    @Transactional
    public AssetsDTO findEthOrBtc(String userId, String tickerSymbol) {
        Assets assets = assetsRepository.findByAssetsCompositeKeyUserIdAndAssetsCompositeKeyTickerSymbol(userId,tickerSymbol).orElse(null);
        if(assets==null)
            return null;
        return new AssetsDTO(assets.getAssetsCompositeKey().getUserId(),assets.getAssetsCompositeKey().getTickerSymbol(),assets.getTickerQuantity());
    }

    @Override
    @Transactional
    public void updateUsdt(AssetsDTO usdt) {
        Assets assets = new Assets();
        AssetsCompositeKey assetsCompositeKey = new AssetsCompositeKey();
        assetsCompositeKey.setUserId(usdt.getUserId());
        assetsCompositeKey.setTickerSymbol(usdt.getTickerSymbol());
        assets.setAssetsCompositeKey(assetsCompositeKey);
        assets.setTickerQuantity(usdt.getValue());
        assetsRepository.save(assets);
    }

    @Override
    @Transactional
    public void addOrUpdateBtcEth(AssetsDTO btcOrEth) {
        Assets assets = new Assets();
        AssetsCompositeKey assetsCompositeKey = new AssetsCompositeKey();
        assetsCompositeKey.setUserId(btcOrEth.getUserId());
        assetsCompositeKey.setTickerSymbol(btcOrEth.getTickerSymbol());
        assets.setAssetsCompositeKey(assetsCompositeKey);
        assets.setTickerQuantity(btcOrEth.getValue());
        assetsRepository.save(assets);
    }

    @Override
    @Transactional
    public List<AssetsDTO> findAllAssetsByUser(String userId) {
        List<AssetsDTO> results = new ArrayList<>();
        List<Assets> assets = assetsRepository.findByAssetsCompositeKeyUserId(userId);
        if(assets.isEmpty())
            throw new UserNotFoundException(userId+ " is not found in records table");
        for(Assets i : assets) {
            results.add(convertEntityToDto(i));
        }
        return results;
    }

    private AssetsDTO convertEntityToDto(Assets assets){
        AssetsDTO data = new AssetsDTO();
        data.setUserId(assets.getAssetsCompositeKey().getUserId());
        data.setTickerSymbol(assets.getAssetsCompositeKey().getTickerSymbol());
        data.setValue(assets.getTickerQuantity());
        return data;
    }
}
