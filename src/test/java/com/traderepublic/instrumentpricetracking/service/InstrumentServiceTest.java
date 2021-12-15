package com.traderepublic.instrumentpricetracking.service;

import com.traderepublic.instrumentpricetracking.integration.model.PartnerData;
import com.traderepublic.instrumentpricetracking.integration.model.PartnerInstrument;
import com.traderepublic.instrumentpricetracking.repository.InstrumentRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class InstrumentServiceTest {

    @Autowired
    private InstrumentService instrumentService;

    @MockBean
    private InstrumentRepository instrumentRepository;

    public static List<PartnerData> testDataAdd(){
        PartnerInstrument partnerInstrument = new PartnerInstrument();
        partnerInstrument.setIsin("12AD123DJF32");
        partnerInstrument.setDescription("Test instrument");

        PartnerData partnerData = new PartnerData();
        partnerData.setData(partnerInstrument);
        partnerData.setType("ADD");

        return Lists.list(partnerData);
    }

    public static List<PartnerData> testDataDelete(){
        PartnerInstrument partnerInstrument = new PartnerInstrument();
        partnerInstrument.setIsin("12AD123DJE27");
        partnerInstrument.setDescription("Test instrument");

        PartnerData partnerData = new PartnerData();
        partnerData.setData(partnerInstrument);
        partnerData.setType("DELETE");

        return Lists.list(partnerData);
    }


    @ParameterizedTest
    @MethodSource(value =  "testDataAdd")
    public void handleInstrumentTestForAdding(PartnerData<PartnerInstrument> testDataAdd) {
        instrumentService.handleInstrument(testDataAdd);
        verify(instrumentRepository,times(1)).save(any());
    }

    @ParameterizedTest
    @MethodSource(value =  "testDataDelete")
    public void handleInstrumentTestForDeletion(PartnerData<PartnerInstrument> testDataDelete) {
        instrumentService.handleInstrument(testDataDelete);
        verify(instrumentRepository,times(1)).deleteById(testDataDelete.getData().getIsin());
    }

}
