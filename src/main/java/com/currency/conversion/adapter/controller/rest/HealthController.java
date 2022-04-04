package com.currency.conversion.adapter.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<?> getExchangeRate() {
        return new ResponseEntity<>("I am fine", HttpStatus.OK);
    }
}
