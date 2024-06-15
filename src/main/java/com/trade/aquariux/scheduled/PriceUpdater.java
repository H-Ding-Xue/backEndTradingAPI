package com.trade.aquariux.scheduled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.aquariux.service.PricesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.trade.aquariux.constants.Constants.*;

@Component
@Slf4j
public class PriceUpdater {

    private final PricesService pricesService;

    @Autowired
    public PriceUpdater(final PricesService pricesService) {
        this.pricesService = pricesService;
    }
    @Scheduled(fixedDelay = 10000)
    public void updatePriceWithBestPrice() throws JsonProcessingException {
        BigDecimal huobiBtcBidPrice,binanceBtcBidPrice,huobiBtcAskPrice,binanceBtcAskPrice,huobiEthBidPrice,binanceEthBidPrice,huobiEthAskPrice,binanceEthAskPrice;
        huobiBtcBidPrice = binanceBtcBidPrice = huobiBtcAskPrice = binanceBtcAskPrice = huobiEthBidPrice = binanceEthBidPrice = huobiEthAskPrice = binanceEthAskPrice =BigDecimal.ZERO;
        RestTemplate restTemplate = new RestTemplate();
        String binanceResult = restTemplate.getForObject(BINANCE_URL, String.class);
        String huobiResult = restTemplate.getForObject(HUOBI_URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode binanceArrayNode = mapper.readTree(binanceResult);
        JsonNode huobiArrayNode = mapper.readTree(huobiResult).get(HUOBI_DATA);

        if (huobiArrayNode.isArray()) {
            int found = 0;
            for (JsonNode node : huobiArrayNode) {
                if (node.get(SYMBOL).asText().equalsIgnoreCase(ETH_SYMBOL)) {
                    huobiEthBidPrice = new BigDecimal(node.get("bid").asText());
                    huobiEthAskPrice = new BigDecimal(node.get("ask").asText());
                    found++;

                }
                else if(node.get(SYMBOL).asText().equalsIgnoreCase(BTC_SYMBOL)){
                    huobiBtcBidPrice = new BigDecimal(node.get("bid").asText());
                    huobiBtcAskPrice = new BigDecimal(node.get("ask").asText());
                    found++;
                }

                if(found==2)
                    break;

            }
        }

        if (binanceArrayNode.isArray()) {
            int found = 0;
            for (JsonNode node : binanceArrayNode) {
                if (node.get(SYMBOL).asText().equalsIgnoreCase(ETH_SYMBOL)) {
                    binanceEthBidPrice = new BigDecimal(node.get("bidPrice").asText());
                    binanceEthAskPrice = new BigDecimal(node.get("askPrice").asText());
                    found++;

                }
                else if(node.get(SYMBOL).asText().equalsIgnoreCase(BTC_SYMBOL)){
                    binanceBtcBidPrice = new BigDecimal(node.get("bidPrice").asText());
                    binanceBtcAskPrice = new BigDecimal(node.get("askPrice").asText());
                    found++;
                }

                if(found==2)
                    break;

            }
        }
        // bid is sell ask is buy
        BigDecimal bestBtcBidPrice,bestBtcAskPrice,bestEthBidPrice,bestEthAskPrice;
        bestBtcBidPrice = binanceBtcBidPrice.max(huobiBtcBidPrice);
        bestBtcAskPrice = huobiBtcAskPrice.min(binanceBtcAskPrice);
        bestEthBidPrice = binanceEthBidPrice.max(huobiEthBidPrice);
        bestEthAskPrice = huobiEthAskPrice.min(binanceEthAskPrice);
        pricesService.inserOrUpdateBTCETH(BTC_SYMBOL,bestBtcBidPrice,bestBtcAskPrice);
        //log.info(BTC_SYMBOL + " "+ bestBtcBidPrice + " "+bestBtcAskPrice);
        pricesService.inserOrUpdateBTCETH(ETH_SYMBOL,bestEthBidPrice,bestEthAskPrice);
        //log.info(ETH_SYMBOL + " "+ bestEthBidPrice + " "+bestEthAskPrice);


    }

}

