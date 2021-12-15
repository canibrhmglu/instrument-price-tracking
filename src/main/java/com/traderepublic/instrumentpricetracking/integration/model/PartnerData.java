package com.traderepublic.instrumentpricetracking.integration.model;

import lombok.Data;

@Data
public class PartnerData<T> {

    private T data;
    private String type;

}
