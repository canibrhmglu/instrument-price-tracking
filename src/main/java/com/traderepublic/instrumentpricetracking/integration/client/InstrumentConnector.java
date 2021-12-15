package com.traderepublic.instrumentpricetracking.integration.client;

import com.traderepublic.instrumentpricetracking.integration.configuration.InstrumentDecoder;
import com.traderepublic.instrumentpricetracking.integration.configuration.InstrumentEncoder;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.service.InstrumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@Slf4j
@ClientEndpoint(decoders = InstrumentDecoder.class, encoders = InstrumentEncoder.class )
@Component
public class InstrumentConnector {

    public InstrumentConnector(@Value("${partner.service.host}") String partnerServiceHost, @Value("${partner.service.instrument.path}") String partnerServiceInstrumentUrl, InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
        try {
            ContainerProvider.getWebSocketContainer().connectToServer(this, URI.create(partnerServiceHost + partnerServiceInstrumentUrl));
        } catch (DeploymentException | IOException e) {
            log.error("Websocket connection couldn't be established with partner service.");
        }
    }

    private final InstrumentService instrumentService;
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose() {
    }

    @OnMessage
    public void onMessage( PartnerData<PartnerInstrument> instrumentPartnerData) {
        instrumentService.handleInstrument(instrumentPartnerData);
    }

    @OnError
    public void disconnected(Session session, Throwable error){
        log.error("Error communicating with server: " + error.getMessage());
    }

}
