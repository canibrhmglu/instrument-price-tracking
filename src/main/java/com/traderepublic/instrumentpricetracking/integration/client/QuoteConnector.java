package com.traderepublic.instrumentpricetracking.integration.client;

import com.traderepublic.instrumentpricetracking.integration.configuration.QuoteDecoder;
import com.traderepublic.instrumentpricetracking.integration.configuration.QuoteEncoder;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerQuote;
import com.traderepublic.instrumentpricetracking.service.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

@Slf4j
@ClientEndpoint(decoders = QuoteDecoder.class, encoders = QuoteEncoder.class )
@Component
public class QuoteConnector {

    public QuoteConnector(@Value("${partner.service.host}") String partnerServiceHost, @Value("${partner.service.quote.path}") String partnerServiceQuotePath, QuoteService quoteService) {
        this.quoteService = quoteService;
        try {
            ContainerProvider.getWebSocketContainer().connectToServer(this, URI.create(partnerServiceHost + partnerServiceQuotePath));
        } catch (DeploymentException | IOException e) {
            log.error("Websocket connection couldn't be established with partner service.");
        }
    }

    private final QuoteService quoteService;
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose() {
    }

    @OnMessage
    public void onMessage(PartnerData<PartnerQuote> quotePartnerData) {
        quoteService.handleQuote(quotePartnerData);
    }

    @OnError
    public void disconnected(Session session, Throwable error){
        log.error("Error communicating with server: " + error.getMessage());
    }

}
