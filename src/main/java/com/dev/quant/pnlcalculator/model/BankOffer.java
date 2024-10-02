package com.dev.quant.pnlcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankOffer {

    private String bankName;
    private int quantity;
    private double price;
}
