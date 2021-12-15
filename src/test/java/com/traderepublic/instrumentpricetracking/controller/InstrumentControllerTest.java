package com.traderepublic.instrumentpricetracking.controller;

import com.traderepublic.instrumentpricetracking.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    @Test
    public void checkResponseStatusTest() throws Exception {
        this.mockMvc.perform(get("/instrumentsLastPrice")).andDo(print()).andExpect(status().isOk());
    }

}
