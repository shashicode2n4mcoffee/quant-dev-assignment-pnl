package com.dev.quant.pnlcalculator.service;

import com.dev.quant.pnlcalculator.model.BankOffer;
import com.dev.quant.pnlcalculator.model.BestPriceResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PricingService {

    private List<BankOffer> bankOffers = new ArrayList<>();

    public PricingService() {
        bankOffers.add(new BankOffer("Bank A", 1, 10.1));
        bankOffers.add(new BankOffer("Bank A", 5, 50.0));
        bankOffers.add(new BankOffer("Bank B", 2, 19.8));
        bankOffers.add(new BankOffer("Bank B", 10, 95.0));
        bankOffers.add(new BankOffer("Bank C", 3, 29.0));
        bankOffers.add(new BankOffer("Bank C", 20, 180.0));
        bankOffers.add(new BankOffer("Bank D", 1, 10.5));
        bankOffers.add(new BankOffer("Bank D", 5, 49.5));
    }

    public Map<Integer, BestPriceResult> calculateBestPrices() {
        int[] targetTiers = {3, 5, 10, 15, 20};

        Map<Integer, BestPriceResult> results = new HashMap<>();

        for (int target : targetTiers) {
            BestPriceResult bestResult = findBestCombinationForTier(target);
            results.put(target, bestResult);
        }

        return results;
    }

    private BestPriceResult findBestCombinationForTier(int target) {
        BestPriceResult bestResult = new BestPriceResult(target);

        backtrack(new HashSet<>(), 0, target, new ArrayList<>(), bestResult);

        return bestResult;
    }

    private void backtrack(Set<String> usedBanks, int currentSum, int target,
                           List<BankOffer> currentOffers, BestPriceResult bestResult) {
        if (currentSum == target) {
            double totalPrice = currentOffers.stream().mapToDouble(BankOffer::getPrice).sum();
            if (totalPrice < bestResult.getTotalPrice()) {
                bestResult.setOffers(new ArrayList<>(currentOffers));
                bestResult.setTotalPrice(totalPrice);
            }
            return;
        }

        if (currentSum > target) {
            return;
        }

        for (BankOffer offer : bankOffers) {
            if (!usedBanks.contains(offer.getBankName()) && currentSum + offer.getQuantity() <= target) {
                currentOffers.add(offer);
                usedBanks.add(offer.getBankName());
                backtrack(usedBanks, currentSum + offer.getQuantity(), target, currentOffers, bestResult);
                usedBanks.remove(offer.getBankName());
                currentOffers.remove(currentOffers.size() - 1);
            }
        }
    }
}
