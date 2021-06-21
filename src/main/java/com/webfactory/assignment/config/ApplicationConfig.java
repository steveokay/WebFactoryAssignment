package com.webfactory.assignment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "assignment.application")
public class ApplicationConfig {

    private Long    fetchSchedulerTime;
    private String  bitcoinUrl;
    private Integer connectionTimeout;
    private String  bitcoinDifferentDatesEndpoint;
    private Integer socketTimeout;
    private String  successMessage;
    private String  errorMessage;

}
