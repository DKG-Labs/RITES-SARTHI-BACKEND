package com.sarthi.entity.CricsPos;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "amendment_po_header")
@Data
public class AmendmentPoHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pokey")
    private String pokey;

    private String poNo;
    private String l5noPo;
    private LocalDateTime poDate;
    private LocalDateTime recvDate;

    private String purchaserCode;
    private String purchaserDetail;

    private String vendorCode;
    private String vendorDetails;
    private String firmDetails;

    private String rlyCode;
    private String rlyShortname;

    private String stockNonstock;
    private String rlyNonrly;
    private String poOrLetter;

    private String inspectingAgency;
    private String poStatus;

    private String itemCat;
    private String itemCatDescr;

    private String poPdfPath;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AmendmentPoItem> items = new ArrayList<>();

}
