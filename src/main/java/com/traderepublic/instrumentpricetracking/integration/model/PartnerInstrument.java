package com.traderepublic.instrumentpricetracking.integration.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PartnerInstrument {

    private String description;
    @NotBlank(message = "isin is mandatory.")
    private String isin;

}
