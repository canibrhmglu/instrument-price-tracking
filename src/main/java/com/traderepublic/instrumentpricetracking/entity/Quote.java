package com.traderepublic.instrumentpricetracking.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Quote")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "instrument_isin", referencedColumnName = "isin")
    private Instrument instrument;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
