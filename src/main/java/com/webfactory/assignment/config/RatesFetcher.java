package com.webfactory.assignment.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webfactory.assignment.services.BitcoinRatesFetcher;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class RatesFetcher {

    @NonNull
    private final ApplicationConfig applicationConfig;

    @NonNull
    private final BitcoinRatesFetcher bitcoinRatesFetcher;

    @Scheduled(fixedDelayString = "${assignment.application.fetchSchedulerTime}")
    public void bitcoinRatesFetcher() throws JsonProcessingException {
        log.info("Fetching new bitcoin rates after every : " + applicationConfig.getFetchSchedulerTime() + " ms");
        log.info("Url For fetching rates " + applicationConfig.getBitcoinUrl());
        bitcoinRatesFetcher.getBitcoinRates();
    }
}
