package com.webfactory.assignment.dtos;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class MultipleDatesDTO {

    private TimeDTO time;
    private String  disclaimer;
    private JsonNode bpi;

}
