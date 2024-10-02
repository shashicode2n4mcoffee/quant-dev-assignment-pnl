package com.dev.quant.pnlcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PnLResult {
    private int quantity;
    private double price;
    private double realizedPnL;
    private double unrealizedPnL;
}