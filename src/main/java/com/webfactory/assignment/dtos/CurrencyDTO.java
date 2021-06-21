package com.webfactory.assignment.dtos;

import lombok.Data;

@Data
public class CurrencyDTO {

    private String code;
    private String rate;
    private String description;
    private Double rate_float;

}
