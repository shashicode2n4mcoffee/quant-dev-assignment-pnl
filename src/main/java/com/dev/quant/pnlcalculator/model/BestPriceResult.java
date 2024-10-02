package com.dev.quant.pnlcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestPriceResult {
    private int targetTier;
    private List<BankOffer> offers = new ArrayList<>();
    private double totalPrice = Double.MAX_VALUE;

    public BestPriceResult(int target) {
    }
}
