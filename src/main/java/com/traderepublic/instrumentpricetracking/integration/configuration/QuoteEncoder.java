package com.traderepublic.instrumentpricetracking.integration.configuration;

import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerQuote;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class QuoteEncoder  implements Encoder.Text<PartnerData<PartnerQuote>>{
    @Override
    public String encode(PartnerData<PartnerQuote> object) throws EncodeException {
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
