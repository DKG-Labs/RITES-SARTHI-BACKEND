package com.sarthi.service.certificate;

import com.sarthi.dto.certificate.RawMaterialCertificateDto;

/**
 * Service interface for generating Inspection Certificates.
 * Handles data aggregation from multiple tables to create certificate data.
 */
public interface CertificateService {

    /**
     * Generate Raw Material Inspection Certificate data by IC Number
     * @param icNumber - Inspection Call Number (e.g., RM-IC-1767772023499)
     * @return RawMaterialCertificateDto with all certificate data
     */
    RawMaterialCertificateDto generateRawMaterialCertificate(String icNumber);
    
    /**
     * Generate Raw Material Inspection Certificate data by Call ID
     * @param callId - Inspection Call ID
     * @return RawMaterialCertificateDto with all certificate data
     */
    RawMaterialCertificateDto generateRawMaterialCertificateById(Long callId);
}

