package com.traderepublic.instrumentpricetracking.service;

import com.traderepublic.instrumentpricetracking.entity.Instrument;
import com.traderepublic.instrumentpricetracking.entity.Quote;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.model.InstrumentPrice;
import com.traderepublic.instrumentpricetracking.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("InstrumentService")
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public void handleInstrument(PartnerData<PartnerInstrument> instrumentPartnerData){

        switch (instrumentPartnerData.getType()) {
            case "ADD" :
                saveInstrument(instrumentPartnerData);
                break;
            case "DELETE" :
                deleteInstrument(instrumentPartnerData.getData().getIsin());
                break;
            default:
                log.error("Unknown type!");
                break;
        }
    }

    public void saveInstrument(PartnerData<PartnerInstrument> instrumentPartnerData) {
        Instrument instrument = Instrument.builder().isin(instrumentPartnerData.getData().getIsin())
                .description(instrumentPartnerData.getData().getDescription())
                .createdDate(LocalDateTime.now())
                .build();

        instrumentRepository.save(instrument);
    }

    public Optional<Instrument> getInstrument(String isin){
        return instrumentRepository.findById(isin);
    }

    public void deleteInstrument(String isin) {
        instrumentRepository.deleteById(isin);
    }

    public List<Instrument> getAllInstrument() {

        return instrumentRepository.findAll();
    }

}
