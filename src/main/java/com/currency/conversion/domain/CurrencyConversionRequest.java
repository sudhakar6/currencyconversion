package com.currency.conversion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRequest {
    private String baseCurrency;
    private String quoteCurrency;
    private Double amount;
}
