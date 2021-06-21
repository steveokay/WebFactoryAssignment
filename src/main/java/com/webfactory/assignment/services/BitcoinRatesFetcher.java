package com.webfactory.assignment.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webfactory.assignment.config.ApplicationConfig;
import com.webfactory.assignment.dtos.BitcoinServiceDTO;
import com.webfactory.assignment.dtos.MultipleDatesDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BitcoinRatesFetcher {

    @NonNull
    private final ObjectMapper mapper;

    @NonNull
    private final ApplicationConfig applicationConfig;

    // fetch current rate
    public BitcoinServiceDTO getBitcoinRates() throws JsonProcessingException {

        log.info("Fetching bitcoin rates from endpoint : " + applicationConfig.getBitcoinUrl());
        BitcoinServiceDTO serviceDTO = null;

        // we will use rest template
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        ResponseEntity<String> response = restTemplate.getForEntity(applicationConfig.getBitcoinUrl(),String.class);

        if(response.getStatusCode() == HttpStatus.OK){

            log.info("Success fetching rates, Response payload >>>>>> " + response.getBody());
            serviceDTO = mapper.readValue(response.getBody(),BitcoinServiceDTO.class);

            // print current fetched rate
            log.info("Current Bitcoin rates : " + serviceDTO.getBpi().getCurrency().getRate_float() + " USD");

        }else{
            log.error("error fetching rates : " + response.getBody());
        }

        return serviceDTO;

    }


    public MultipleDatesDTO fetchRatesBetween(String startDate, String endDate) throws JsonProcessingException {

        MultipleDatesDTO serviceDTO = null;

        log.info("Fetching bitcoin rates from dates : " + startDate + " to date : " + endDate);

        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

        ResponseEntity<String> response = restTemplate.getForEntity(applicationConfig.getBitcoinDifferentDatesEndpoint()+"?start="+startDate+"&end="+endDate,String.class);

        if(response.getStatusCode() == HttpStatus.OK){
            log.info("Success fetching rates, Response payload >>>>>> " + response.getBody());

            mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            serviceDTO = mapper.readValue(response.getBody(), MultipleDatesDTO.class);

            log.info(serviceDTO.toString());

        }else{
            log.error("Error fetching rates ::: " + response.getBody());
        }

        return serviceDTO;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory(){
        // rest template config class to setup the timeouts(both socket and conn timeouts
        int requestTimeout = applicationConfig.getConnectionTimeout() + applicationConfig.getSocketTimeout();
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(applicationConfig.getConnectionTimeout())
                .setConnectionRequestTimeout(requestTimeout)
                .setSocketTimeout(applicationConfig.getSocketTimeout())
                .build();
        CloseableHttpClient client = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }
}
