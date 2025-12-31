package com.sarthi.service.Impl.CrisService;

import com.sarthi.entity.CricsPos.PoCancellationDetail;
import com.sarthi.entity.CricsPos.PoCancellationHeader;
import com.sarthi.repository.PoCancellationHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PoCancellationPersistService {

    @Autowired
    private PoCancellationHeaderRepository headerRepo;

    public void save(Map<String, Object> data) {

        Map<String, Object> hdr =
                (Map<String, Object>) data.get("cancellationHeader");
        if (hdr == null) return;

        String cakey = hdr.get("CAKEY").toString();

        if (headerRepo.existsByCakey(cakey)) return;

        List<Map<String, Object>> dtls =
                (List<Map<String, Object>>) data.get("cancellationDetail");

        PoCancellationHeader h = new PoCancellationHeader();

        h.setRly((String) hdr.get("RLY"));
        h.setCakey(cakey);
        h.setCakeyDate(parseDate(hdr.get("CAKEY_DATE")));
        h.setPokey((String) hdr.get("POKEY"));
        h.setPoNo((String) hdr.get("PO_NO"));
        h.setCaNo((String) hdr.get("CA_NO"));
        h.setCaDate(parseDate(hdr.get("CA_DATE")));
        h.setCaType((String) hdr.get("CA_TYPE"));
        h.setVcode((String) hdr.get("VCODE"));
        h.setRefNo((String) hdr.get("REF_NO"));
        h.setRefDate(parseDate(hdr.get("REF_DATE")));
        h.setRemarks((String) hdr.get("REMARKS"));
        h.setCaSignOff((String) hdr.get("CA_SIGN_OFF"));
        h.setStatus((String) hdr.get("STATUS"));
        h.setPurDiv((String) hdr.get("PUR_DIV"));
        h.setPurSec((String) hdr.get("PUR_SEC"));
        h.setPoMaSrno((String) hdr.get("PO_MA_SRNO"));
        h.setCaReason((String) hdr.get("CA_REASON"));

        headerRepo.save(h);

        if (dtls != null) {
            for (Map<String, Object> m : dtls) {

                PoCancellationDetail d = new PoCancellationDetail();
                d.setHeader(h);
                h.getDetails().add(d);

                d.setRly((String) m.get("RLY"));
                d.setCakey((String) m.get("CAKEY"));
                d.setSlno((String) m.get("SLNO"));
                d.setPlNo((String) m.get("PL_NO"));
                d.setPoSr((String) m.get("PO_SR"));
                d.setPoBalQty(toBD(m.get("PO_BAL_QTY")));
                d.setCancQty(toBD(m.get("CANC_QTY")));
                d.setStatus((String) m.get("STATUS"));
                d.setDemStatus((String) m.get("DEM_STATUS"));
            }
        }
    }

    private BigDecimal toBD(Object o) {
        return o == null ? null : new BigDecimal(o.toString());
    }

    private LocalDate parseDate(Object o) {
        if (o == null) return null;
        return LocalDate.parse(
                o.toString(),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        );
    }
}
