package com.webfactory.assignment.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BpiDTO {

    @JsonProperty("USD")
    private CurrencyDTO currency;

}
