package com.webfactory.assignment.dtos;

import lombok.Data;

@Data
public class BitcoinServiceDTO {

    private TimeDTO time;
    private String  disclaimer;
    private BpiDTO  bpi;

}
