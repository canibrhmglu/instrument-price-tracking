package com.traderepublic.instrumentpricetracking.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CandleStick {

    @NotBlank(message = "isin is mandatory.")
    private String isin;
    private LocalDateTime openTimestamp;
    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closePrice;
    private LocalDateTime closeTimestamp;

}
