package com.trade.aquariux.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trade.aquariux.dto.*;
import com.trade.aquariux.exception.CoinNotOwnedException;
import com.trade.aquariux.exception.NotEnoughBuyingPowerException;
import com.trade.aquariux.scheduled.PriceUpdater;
import com.trade.aquariux.service.AssetsService;
import com.trade.aquariux.service.HistoricTradeService;
import com.trade.aquariux.service.PricesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static com.trade.aquariux.constants.Constants.*;

@Slf4j
@RestController
public class TradeController {
    private final PriceUpdater priceUpdater;
    private final PricesService pricesService;
    private final AssetsService assetsService;
    private final HistoricTradeService historicTradeService;

    @Autowired
    public TradeController(final PriceUpdater priceUpdater, final PricesService pricesService, final AssetsService assetsService, final HistoricTradeService historicTradeService) {
        this.priceUpdater = priceUpdater;
        this.pricesService = pricesService;
        this.assetsService = assetsService;
        this.historicTradeService = historicTradeService;
    }

    @GetMapping("/prices")
    public ResponseEntity<PriceDTO> getBestPrice(@RequestParam String tickerSymbol) throws JsonProcessingException {
        priceUpdater.updatePriceWithBestPrice();
        PriceDTO priceDTO = pricesService.getBestPrice(tickerSymbol.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK).body(priceDTO);
    }

    @RequestMapping(value = "/trade", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<ResponseDTO> trade(@RequestBody TradeDTO tradeDTO) throws JsonProcessingException {
        // bid is sell ask is buy
        /*
        use the below json to test there is only user1 as a single default user
        {
            "tradeType" : "buy/SELL",
            "tickerSymbol" : "ETHUSDT/BTCUSDT",
            "tradeSize" : 1,
            "tradedBy" : "user1"
        }
         */

        priceUpdater.updatePriceWithBestPrice();
        PriceDTO priceDTO = pricesService.getBestPrice(tradeDTO.getTickerSymbol().toUpperCase());
        if (tradeDTO.getTradeType().equalsIgnoreCase(BUY)) {
            AssetsDTO usdt = assetsService.findBuyingPower(tradeDTO.getTradedBy(), USDT_SYMBOL);
            BigDecimal buyingPower = usdt.getValue();
            BigDecimal amountToBuy = tradeDTO.getTradeSize().multiply(priceDTO.getAskPrice());
            if (buyingPower.compareTo(amountToBuy) < 0)
                throw new NotEnoughBuyingPowerException("Not enough buying power " + "Buying power:" + buyingPower + " Amount to Buy:" + amountToBuy);
            usdt.setValue(buyingPower.subtract(amountToBuy));
            assetsService.updateUsdt(usdt);

            AssetsDTO btcOrEth = assetsService.findEthOrBtc(tradeDTO.getTradedBy(), tradeDTO.getTickerSymbol().toUpperCase());
            if (btcOrEth == null) {
                btcOrEth = new AssetsDTO(tradeDTO.getTradedBy(), tradeDTO.getTickerSymbol(), tradeDTO.getTradeSize());
                assetsService.addOrUpdateBtcEth(btcOrEth);
                historicTradeService.historicRecord(tradeDTO, priceDTO.getAskPrice());
            } else {
                btcOrEth.setValue(btcOrEth.getValue().add(tradeDTO.getTradeSize()));
                assetsService.addOrUpdateBtcEth(btcOrEth);
                historicTradeService.historicRecord(tradeDTO, priceDTO.getAskPrice());
            }

        } else if (tradeDTO.getTradeType().equalsIgnoreCase(SELL)) {
            AssetsDTO btcOrEth = assetsService.findEthOrBtc(tradeDTO.getTradedBy(), tradeDTO.getTickerSymbol().toUpperCase());
            if (btcOrEth == null) {
                throw new CoinNotOwnedException("You do not own the coin you are trying to sell");
            } else {
                if (btcOrEth.getValue().compareTo(tradeDTO.getTradeSize()) < 0)
                    throw new NotEnoughBuyingPowerException("Not enough selling power " + "Selling power:" + btcOrEth.getValue() + " Amount to Buy:" + tradeDTO.getTradeSize());

                btcOrEth.setValue(btcOrEth.getValue().subtract(tradeDTO.getTradeSize()));
                assetsService.addOrUpdateBtcEth(btcOrEth);
                AssetsDTO usdt = assetsService.findBuyingPower(tradeDTO.getTradedBy(), USDT_SYMBOL);
                usdt.setValue(usdt.getValue().add(priceDTO.getBidPrice().multiply(tradeDTO.getTradeSize())));
                assetsService.updateUsdt(usdt);
                historicTradeService.historicRecord(tradeDTO, priceDTO.getBidPrice());
            }
        }

        ResponseDTO responseDTO = new ResponseDTO(RESPONSE_200, TRADE_SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);


    }

    @GetMapping("/trades")
    //there is only one default user inserted on startup so use http://localhost:8080/assets?userId=user1 to test
    public ResponseEntity<List<HistoricRecordDTO>> getHistoricTrades(@RequestParam String userId) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(historicTradeService.getAllHistoricRecords(userId));
    }

    @GetMapping("/assets")
    //there is only one default user inserted on startup so use http://localhost:8080/assets?userId=user1 to test
    public ResponseEntity<List<AssetsDTO>> getAllAssetsByUser(@RequestParam String userId) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(assetsService.findAllAssetsByUser(userId));
    }


}
