package com.traderepublic.instrumentpricetracking.integration.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class PartnerQuote {

    @NotBlank(message = "isin is mandatory.")
    private String isin;
    @NotBlank(message = "price is mandatory.")
    private BigDecimal price;

}
