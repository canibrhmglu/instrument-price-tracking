package com.traderepublic.instrumentpricetracking;

import com.traderepublic.instrumentpricetracking.controller.InstrumentController;
import com.traderepublic.instrumentpricetracking.controller.QuoteController;
import com.traderepublic.instrumentpricetracking.integration.client.InstrumentConnector;
import com.traderepublic.instrumentpricetracking.integration.client.QuoteConnector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

    @Autowired
    private QuoteController quoteController;

    @Autowired
    private InstrumentController instrumentController;

    @Autowired
    private QuoteConnector quoteConnector;

    @Autowired
    private InstrumentConnector instrumentConnector;

    @Test
    void contextLoads() throws Exception {
        assertThat(quoteController).isNotNull();
        assertThat(instrumentController).isNotNull();
        assertThat(quoteConnector).isNotNull();
        assertThat(instrumentConnector).isNotNull();
    }

}
