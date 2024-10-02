package com.dev.quant.pnlcalculator.controller;

import com.dev.quant.pnlcalculator.model.BestPriceResult;
import com.dev.quant.pnlcalculator.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @GetMapping("/best-prices")
    public ResponseEntity<Map<Integer, BestPriceResult>> getBestPrices() {
        Map<Integer, BestPriceResult> bestPrices = pricingService.calculateBestPrices();
        return ResponseEntity.ok(bestPrices);
    }
}
