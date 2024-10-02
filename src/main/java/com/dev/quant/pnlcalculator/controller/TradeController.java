package com.dev.quant.pnlcalculator.controller;

import com.dev.quant.pnlcalculator.model.PnLResult;
import com.dev.quant.pnlcalculator.model.Trade;
import com.dev.quant.pnlcalculator.service.TradeService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/trades")
@Slf4j
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadTrades(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a non-empty CSV file.");
        }

        if (!StringUtils.getFilenameExtension(file.getOriginalFilename()).equalsIgnoreCase("csv")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV file.");
        }

        List<PnLResult> pnlResults = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream())); CSVReader csvReader = new CSVReader(reader)) {

            String[] nextLine;
            csvReader.readNext();
            tradeService.reset();

            while ((nextLine = csvReader.readNext()) != null) {
                if (nextLine.length < 2) {
                    continue;
                }

                int quantity = Integer.parseInt(nextLine[0].trim());
                double price = Double.parseDouble(nextLine[1].trim());

                Trade trade = new Trade(quantity, price);
                PnLResult result = tradeService.processTrade(trade);
                pnlResults.add(result);
            }

            return ResponseEntity.ok(pnlResults);

        } catch (IOException | CsvValidationException | NumberFormatException e) {
            log.error("Error processing the CSV file.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the CSV file.");
        }
    }

}