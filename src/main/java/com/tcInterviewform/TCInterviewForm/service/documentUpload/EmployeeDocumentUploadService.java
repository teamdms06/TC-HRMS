/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.HrmsDocumentCenter;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsDocumentUploadLink;
import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsEmployeeDocument;
import com.tcInterviewform.TCInterviewForm.repository.documentUpload.HrmsEmployeeDocumentRepository;
import com.tcInterviewform.TCInterviewForm.service.S3Service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author teamd
 */
@Service
public class EmployeeDocumentUploadService {

    @Autowired
    private EmployeeDocumentLinkService linkService;

    @Autowired
    private HrmsEmployeeDocumentRepository documentRepo;

    @Autowired
    private S3Service s3Service;

    @Transactional(rollbackFor = Exception.class)
    public String uploadDocument(
            String token,
            String documentType,
            MultipartFile file
    ) throws Exception {

        HrmsDocumentUploadLink link = linkService.validateToken(token);

        HrmsEmployeeMaster emp = link.getEmployeeMaster();
//
//        String fileName = documentType.toLowerCase().replace(" ", "_")
//                + "_"
//                + System.currentTimeMillis()
//                + "_"
//                + file.getOriginalFilename();

//        String s3Key = "employee_documents/"
//                + emp.getEmployeeId()
//                + "/"
//                + documentType.toLowerCase().replace(" ", "_")
//                + "/"
//                + fileName;
        String s3Key = s3Service.uploadEmployeeDocment(emp.getEmployeeId(), documentType, file);

        HrmsEmployeeDocument doc = new HrmsEmployeeDocument();

        doc.setEmployeeMaster(emp);
        doc.setDocumentType(documentType);
        doc.setFileName(file.getOriginalFilename());
        doc.setS3Path(s3Key);
        doc.setStatus("UPLOADED");
        doc.setUploadedAt(LocalDateTime.now());
        documentRepo.save(doc);

        return documentType + " uploaded successfully";
    }

    public List<HrmsEmployeeDocument> getEmployeeDocuments(Long employeeId) {
        return documentRepo.getLatestDocumentsByEmployee(employeeId);
    }

    @Transactional(rollbackFor = Exception.class)
    public String verifyUploadedDocument(
            Long documentId,
            String status,
            String verifiedBy) {

        if (!status.equalsIgnoreCase("VERIFIED")
                && !status.equalsIgnoreCase("REJECTED")) {
            throw new RuntimeException("Invalid verification status");
        }

        HrmsEmployeeDocument doc = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        doc.setVerificationStatus(status.toUpperCase());
        doc.setVerifiedBy(verifiedBy);
        doc.setVerifiedAt(LocalDateTime.now());

        documentRepo.save(doc);

        return "Document " + status.toLowerCase() + " successfully";
    }
    
   
}
