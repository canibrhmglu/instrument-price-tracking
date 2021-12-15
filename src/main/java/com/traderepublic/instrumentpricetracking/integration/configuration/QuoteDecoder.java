package com.traderepublic.instrumentpricetracking.integration.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerQuote;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.lang.reflect.Type;

public class QuoteDecoder  implements Decoder.Text<PartnerData<PartnerQuote>> {
    @Override
    public PartnerData<PartnerQuote> decode(String s) throws DecodeException {

            ObjectMapper mapper = new ObjectMapper();
            PartnerData<PartnerQuote> partnerData = null;
            try {
                partnerData = mapper.readValue(s, new TypeReference<PartnerData<PartnerQuote>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return partnerData;

    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
