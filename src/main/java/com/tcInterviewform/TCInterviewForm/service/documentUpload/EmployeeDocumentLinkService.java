/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsDocumentUploadLink;
import com.tcInterviewform.TCInterviewForm.repository.documentUpload.HrmsDocumentUploadLinkRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author teamd
 */
@Service
public class EmployeeDocumentLinkService {

    @Autowired
    private HrmsEmployeeMasterRepository employeeRepo;

    @Autowired
    private HrmsDocumentUploadLinkRepository uploadLinkRepo;

    public String generateUploadLink(Long employeeId) {

        HrmsEmployeeMaster emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        String token = UUID.randomUUID().toString();

        HrmsDocumentUploadLink link = new HrmsDocumentUploadLink();

        link.setEmployeeMaster(emp);
        link.setToken(token);
        link.setExpiryTime(LocalDateTime.now().plusDays(2));
        link.setStatus("ACTIVE");

        uploadLinkRepo.save(link);

//        return "http://app.the-connections.com/document-upload?token=" + token;
        return "http://localhost:8091/document-upload?token=" + token;
    }

    public HrmsDocumentUploadLink validateToken(String token) {

        HrmsDocumentUploadLink link = uploadLinkRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid upload link"));

        if (!"ACTIVE".equalsIgnoreCase(link.getStatus())) {
            throw new RuntimeException("Upload link is inactive");
        }

        if (link.getExpiryTime().isBefore(LocalDateTime.now())) {
            link.setStatus("EXPIRED");
            uploadLinkRepo.save(link);

            throw new RuntimeException("Upload link expired");
        }

        return link;
    }
    
}
