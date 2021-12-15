package com.traderepublic.instrumentpricetracking.service;

import com.traderepublic.instrumentpricetracking.entity.Instrument;
import com.traderepublic.instrumentpricetracking.entity.Quote;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.model.CandleStick;
import com.traderepublic.instrumentpricetracking.repository.QuoteRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuoteServiceTest {

        @Autowired
        private  QuoteService quoteService;

        @MockBean
        private QuoteRepository quoteRepository;

        public static List<Quote> testData(){
                Instrument instrument = Instrument.builder().isin("GD2859403B46")
                        .description("Test")
                        .build();

                Quote quote1 = Quote.builder().instrument(instrument)
                        .price(new BigDecimal("111.2"))
                        .createdDate(LocalDateTime.now())
                        .build();
                Quote quote2 = Quote.builder().instrument(instrument)
                        .price(new BigDecimal("110.4"))
                        .createdDate(LocalDateTime.now().minusMinutes(1))
                        .build();
                Quote quote3 = Quote.builder().instrument(instrument)
                        .price(new BigDecimal("110.8"))
                        .createdDate(LocalDateTime.now().minusMinutes(1))
                        .build();
                Quote quote4 = Quote.builder().instrument(instrument)
                        .price(new BigDecimal("113.2"))
                        .createdDate(LocalDateTime.now().minusMinutes(1))
                        .build();

                return Lists.list(quote1,quote2,quote3,quote4);
        }

        @Test
        public void getCandleSticksTest(){
                when(quoteRepository.findByIsinAndCreatedDateBetween(testData().get(0).getInstrument().getIsin(),
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(1),
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                ).thenReturn(testData());
                List<CandleStick> candleSticks = quoteService.getCandleSticks(testData().get(0).getInstrument().getIsin());
                assertEquals(new BigDecimal("113.2"), candleSticks.get(0).getHighPrice());
                assertEquals(new BigDecimal("110.4"), candleSticks.get(0).getLowPrice());
        }

        @Test
        public void getCandleSticksTestForGettingValuesFromPreviousCandle(){
                when(quoteRepository.findByIsinAndCreatedDateBetween(testData().get(0).getInstrument().getIsin(),
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(3),
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(2))
                ).thenReturn(testData());
                List<CandleStick> candleSticks = quoteService.getCandleSticks(testData().get(0).getInstrument().getIsin());
                assertEquals(4, candleSticks.size());

        }

}
