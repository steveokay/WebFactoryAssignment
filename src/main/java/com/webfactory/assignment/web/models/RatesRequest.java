package com.webfactory.assignment.web.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RatesRequest {

    @NotNull(message = "Payload must contain startDate")
    @NotEmpty(message = "Payload must contain startDate")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "startDate must be of the format yyyy-mm-dd")
    private String startDate;

    @NotNull(message = "Payload must contain endDate")
    @NotEmpty(message = "Payload must contain endDate")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "endDate must be of the format yyyy-mm-dd")
    private String endDate;

}
