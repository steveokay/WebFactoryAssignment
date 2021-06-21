package com.webfactory.assignment.web.models;

import lombok.Data;

@Data
public class RatesResponse {

    private Double rates;
    private String statusCode;
    private String description;

}
