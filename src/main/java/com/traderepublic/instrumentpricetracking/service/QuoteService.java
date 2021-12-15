package com.traderepublic.instrumentpricetracking.service;

import com.traderepublic.instrumentpricetracking.entity.Instrument;
import com.traderepublic.instrumentpricetracking.entity.Quote;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerQuote;
import com.traderepublic.instrumentpricetracking.model.CandleStick;
import com.traderepublic.instrumentpricetracking.model.InstrumentPrice;
import com.traderepublic.instrumentpricetracking.repository.QuoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service("QuoteService")
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final InstrumentService instrumentService;

    public void handleQuote( PartnerData<PartnerQuote> quotePartnerData){

        Optional<Instrument> instrument = instrumentService.getInstrument(quotePartnerData.getData().getIsin());

        if(instrument.isPresent()) {
            Quote quote = Quote.builder().instrument(instrument.get())
                    .price(quotePartnerData.getData().getPrice())
                    .createdDate(LocalDateTime.now())
                    .build();

            quoteRepository.save(quote);
        } else {
            log.info("The instrument of quote with isin " + quotePartnerData.getData().getIsin() +" is not found!");
        }
    }

    public Optional<Quote> findFirstByCreatedDate(String isin) {
        return quoteRepository.findFirstByCreatedDate(isin);
    }

    public List<CandleStick> getCandleSticks(String isin) {

        CandleStick candleStick = null;
        List<CandleStick> candleSticks = new ArrayList<>();

        for(int i =30; i >=0; i --) {

            LocalDateTime startDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(i);
            LocalDateTime endDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).minusMinutes(i-1);

            List<Quote> quotes = quoteRepository.findByIsinAndCreatedDateBetween(isin, startDate, endDate);

            if(!quotes.isEmpty()) {
                 candleStick = CandleStick.builder().isin(isin)
                        .openTimestamp(startDate)
                        .openPrice(quotes.get(0).getPrice())
                        .closePrice(quotes.get(quotes.size() - 1).getPrice())
                        .highPrice(quotes.stream().map(quote -> quote.getPrice())
                                .reduce(((x, y) -> x.compareTo(y) == 1 ? x:y)).get())
                        .lowPrice(quotes.stream().map(quote -> quote.getPrice())
                                .reduce(((x, y) -> x.compareTo(y) == -1 ? x:y)).get())
                        .closeTimestamp(endDate).build();

                candleSticks.add(candleStick);
                //There weren’t any prices received, values from the previous candle are reused.
            } else if(!candleSticks.isEmpty()) {
                candleStick = CandleStick.builder().isin(isin)
                        .openTimestamp(startDate)
                        .openPrice(candleSticks.get(candleSticks.size() -1).getOpenPrice())
                        .closePrice(candleSticks.get(candleSticks.size() -1).getClosePrice())
                        .highPrice(candleSticks.get(candleSticks.size() -1).getHighPrice())
                        .lowPrice(candleSticks.get(candleSticks.size() -1).getLowPrice())
                        .closeTimestamp(endDate).build();

                candleSticks.add(candleStick);
            }

        }

        return candleSticks;
    }

    public List<InstrumentPrice> getLastQuote(){
        List<Instrument> instruments = instrumentService.getAllInstrument();

        List<InstrumentPrice> instrumentPrices = instruments.stream().map(instrument -> instrument.getIsin())
                .map(isin -> new InstrumentPrice(isin))
                .map(instrumentPrice -> {
                    Optional<Quote> instrumentLastPrice = this.findFirstByCreatedDate(instrumentPrice.getIsin());
                    if(instrumentLastPrice.isPresent()) {
                        Quote quote = instrumentLastPrice.get();
                        instrumentPrice.setLastPrice(quote.getPrice());
                        instrumentPrice.setCreatedDate(quote.getCreatedDate());
                    }
                    return instrumentPrice;
                }).collect(Collectors.toList());

        return instrumentPrices;
    }

}
