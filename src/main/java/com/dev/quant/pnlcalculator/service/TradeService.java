package com.dev.quant.pnlcalculator.service;

import com.dev.quant.pnlcalculator.model.PnLResult;
import com.dev.quant.pnlcalculator.model.Trade;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TradeService {

    private final LinkedList<Trade> positionQueue = new LinkedList<>();
    private double cumulativeRealizedPnL = 0.0;
    private double cumulativeUnrealizedPnL = 0.0;

    public PnLResult processTrade(Trade trade) {
        double realizedPnL = 0.0;
        int tradeQty = trade.getQuantity();
        double tradePrice = trade.getPrice();

        if (tradeQty > 0) {
            positionQueue.addLast(new Trade(tradeQty, tradePrice));
        } else {
            int sellQty = Math.abs(tradeQty);
            while (sellQty > 0 && !positionQueue.isEmpty()) {
                Trade buyTrade = positionQueue.peekFirst();
                int availableQty = buyTrade.getQuantity();

                if (availableQty <= sellQty) {
                    realizedPnL += (tradePrice - buyTrade.getPrice()) * availableQty;
                    sellQty -= availableQty;
                    positionQueue.pollFirst();
                } else {
                    realizedPnL += (tradePrice - buyTrade.getPrice()) * sellQty;
                    buyTrade.setQuantity(availableQty - sellQty);
                    sellQty = 0;
                }
            }
        }

        cumulativeRealizedPnL += realizedPnL;

        double unrealizedPnL = 0.0;
        for (Trade openTrade : positionQueue) {
            unrealizedPnL += (tradePrice - openTrade.getPrice()) * openTrade.getQuantity();
        }
        cumulativeUnrealizedPnL = unrealizedPnL;

        return new PnLResult(tradeQty, tradePrice, cumulativeRealizedPnL, cumulativeUnrealizedPnL);
    }

    public void reset() {
        positionQueue.clear();
        cumulativeRealizedPnL = 0.0;
        cumulativeUnrealizedPnL = 0.0;
    }
}
