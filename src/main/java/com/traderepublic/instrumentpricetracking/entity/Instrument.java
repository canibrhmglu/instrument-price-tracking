package com.traderepublic.instrumentpricetracking.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Instrument")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Instrument {

    @Id
    @Column(name = "isin", length = 12, nullable = false)
    private String isin;

    @Column(name = "description", length = 200)
    private String description;

    @OneToMany(mappedBy = "instrument", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Quote> quotes;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
