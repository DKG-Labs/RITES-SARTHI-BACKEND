package com.sarthi.entity.CricsPos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "po_ma_detail")
@Data
public class PoMaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rly;

    private String slno;
    private String maFld;
    private String maFldDescr;

    private String oldValue;
    private String newValue;

    private String newValueInd;
    private String newValueFlag;

    private String plNo;
    private String poSr;

    private String condSlno;
    private String condCode;

    private String maSrNo;
    private String status;

    private String sourceSystem = "CRIS";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ma_key", referencedColumnName = "ma_key")
    private PoMaHeader maHeader;
}
