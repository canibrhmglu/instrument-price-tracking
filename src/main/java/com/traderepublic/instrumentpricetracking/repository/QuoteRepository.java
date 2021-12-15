package com.traderepublic.instrumentpricetracking.repository;

import com.traderepublic.instrumentpricetracking.entity.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, Long> {

    @Query(value = "SELECT * FROM Quote where instrument_isin = ? order by created_date limit 1",nativeQuery = true)
    Optional<Quote> findFirstByCreatedDate(String isin);

    @Query(value = "SELECT q FROM Quote q where instrument_isin = ?1 and created_date >= ?2 and created_date < ?3  order by created_date asc")
    List<Quote> findByIsinAndCreatedDateBetween(String isin, LocalDateTime startDate, LocalDateTime endDate);

}
