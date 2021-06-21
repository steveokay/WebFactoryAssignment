package com.webfactory.assignment.web.models;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class MultipleDatesResponse {

    private JsonNode rates;
    private String statusCode;
    private String description;

}
