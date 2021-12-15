package com.traderepublic.instrumentpricetracking.repository;

import com.traderepublic.instrumentpricetracking.entity.Instrument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, String> {

    List<Instrument> findAll();

}
