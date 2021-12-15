package com.traderepublic.instrumentpricetracking.controller;

import com.traderepublic.instrumentpricetracking.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    QuoteService quoteService;

    @Test
    public void checkResponseForWrongIsinTest() throws Exception {
        this.mockMvc.perform(get("/candleSticksByInstrument").param("isin","AS12DS7459"))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void checkResponseStatusTest() throws Exception {
        this.mockMvc.perform(get("/candleSticksByInstrument").param("isin","AS12DS745923"))
                .andDo(print()).andExpect(status().isOk());
    }

}
