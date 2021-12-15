package com.traderepublic.instrumentpricetracking.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InstrumentPrice {

    public InstrumentPrice(String isin) {
        this.setIsin(isin);
    }

    @NotBlank(message = "isin is mandatory.")
    private String isin;
    private BigDecimal lastPrice;
    private LocalDateTime createdDate;
}
