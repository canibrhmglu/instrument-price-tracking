package com.traderepublic.instrumentpricetracking.integration.configuration;

import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class InstrumentEncoder implements Encoder.Text<PartnerData<PartnerInstrument>> {

    @Override
    public String encode(PartnerData<PartnerInstrument> object) throws EncodeException {
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
