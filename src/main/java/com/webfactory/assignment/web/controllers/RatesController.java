package com.webfactory.assignment.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webfactory.assignment.config.ApplicationConfig;
import com.webfactory.assignment.dtos.BitcoinServiceDTO;
import com.webfactory.assignment.dtos.MultipleDatesDTO;
import com.webfactory.assignment.services.BitcoinRatesFetcher;
import com.webfactory.assignment.web.models.MultipleDatesResponse;
import com.webfactory.assignment.web.models.RatesRequest;
import com.webfactory.assignment.web.models.RatesResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rates")
public class RatesController {

    @NonNull
    private final BitcoinRatesFetcher ratesFetcher;

    @NonNull
    private final ApplicationConfig applicationConfig;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatesResponse> getLatestRate() throws JsonProcessingException {

        // call endpoint to fetch rates
        BitcoinServiceDTO rates = ratesFetcher.getBitcoinRates();

        RatesResponse response = new RatesResponse();

        // return success and rate if rates is not null
        if(rates != null ){
            response.setRates(Double.parseDouble(rates.getBpi().getCurrency().getRate().replaceAll(",","")));
            response.setDescription(applicationConfig.getSuccessMessage());
            response.setStatusCode(Integer.toString(HttpStatus.OK.value()));

        }else{
            response.setRates(0D);
            response.setDescription(applicationConfig.getErrorMessage());
            response.setStatusCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MultipleDatesResponse> getRatesBetween(@Valid @RequestBody RatesRequest payload) throws JsonProcessingException {

        log.info("Payload Received :::::: " + payload.toString());

        MultipleDatesDTO rates = ratesFetcher.fetchRatesBetween(payload.getStartDate(), payload.getEndDate());

        MultipleDatesResponse response = new MultipleDatesResponse();

        if(rates.getBpi() != null || rates.getBpi().toString() != ""){
            response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
            response.setRates(rates.getBpi());
            response.setDescription(applicationConfig.getSuccessMessage());
        }else{
            response.setStatusCode(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            response.setDescription(applicationConfig.getErrorMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
