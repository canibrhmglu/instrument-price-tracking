package com.traderepublic.instrumentpricetracking.controller;

import com.traderepublic.instrumentpricetracking.model.CandleStick;
import com.traderepublic.instrumentpricetracking.service.QuoteService;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Validated
@ControllerAdvice
@AllArgsConstructor
@Controller
public class QuoteController {
    private final QuoteService quoteService;

    @RequestMapping(path = "/candleSticksByInstrument", method = RequestMethod.GET)
    public
    ResponseEntity<List<CandleStick>> getInstrumentPrice(@RequestParam("isin") @Length(max = 12, min = 12) String isin){
        return ResponseEntity.status(HttpStatus.OK).body(quoteService.getCandleSticks(isin));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(javax.validation.ConstraintViolationException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity(details, HttpStatus.BAD_REQUEST);
    }
}
