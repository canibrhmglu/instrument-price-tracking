package com.traderepublic.instrumentpricetracking.controller;


import com.traderepublic.instrumentpricetracking.model.InstrumentPrice;
import com.traderepublic.instrumentpricetracking.service.InstrumentService;
import com.traderepublic.instrumentpricetracking.service.QuoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@ControllerAdvice
@AllArgsConstructor
@Controller
public class InstrumentController {

    private final QuoteService quoteService;

    @RequestMapping(path = "/instrumentsLastPrice", method = RequestMethod.GET)
    public @ResponseBody
    List<InstrumentPrice> getInstrumentPrice(){
        return quoteService.getLastQuote();
    }

}
