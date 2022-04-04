package com.currency.conversion.adapter.controller.rest;

import com.currency.conversion.domain.CurrencyConversionRequest;
import com.currency.conversion.service.CurrencyConvertor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CurrencyController {

    private final CurrencyConvertor currencyConvertor;

    public CurrencyController(CurrencyConvertor currencyConvertor) {
        this.currencyConvertor = currencyConvertor;
    }

    @GetMapping("/exchange-rate")
    public ResponseEntity<?> getExchangeRate(@RequestParam(name = "base") String base, @RequestParam String quote) {
        return new ResponseEntity<>(currencyConvertor.getExchangeRate(base, quote), HttpStatus.OK);
    }

    @PostMapping("/currency-converter")
    public ResponseEntity<?> getExchangeRate(@RequestBody CurrencyConversionRequest conversionRequest) {
        return new ResponseEntity<>(currencyConvertor.convertCurrency(conversionRequest.getAmount(), conversionRequest.getBaseCurrency(), conversionRequest.getQuoteCurrency()), HttpStatus.OK);
    }
}
