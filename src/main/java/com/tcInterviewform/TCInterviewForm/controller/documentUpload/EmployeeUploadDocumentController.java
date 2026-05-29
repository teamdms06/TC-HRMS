/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsDocumentUploadLink;
import com.tcInterviewform.TCInterviewForm.model.documentUpload.HrmsEmployeeDocument;
import com.tcInterviewform.TCInterviewForm.repository.documentUpload.HrmsEmployeeDocumentRepository;
import com.tcInterviewform.TCInterviewForm.service.S3Service;
import com.tcInterviewform.TCInterviewForm.service.documentUpload.EmployeeDocumentLinkService;
import com.tcInterviewform.TCInterviewForm.service.documentUpload.EmployeeDocumentMailService;
import com.tcInterviewform.TCInterviewForm.service.documentUpload.EmployeeDocumentUploadService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author teamd
 */
@Controller
@RequiredArgsConstructor
public class EmployeeUploadDocumentController {
     private final EmployeeDocumentUploadService employeeDocumentUploadService;
     private final EmployeeDocumentMailService employeeDocumentMailService;
     private final EmployeeDocumentLinkService linkService;
     private final HrmsEmployeeDocumentRepository documentRepo;
     private final S3Service s3Service;
     @PostMapping("/send-document-upload-link")
@ResponseBody
public ResponseEntity<?> sendDocumentUploadLink(
        HttpServletRequest request,
        @RequestParam Long employeeId,
        Locale locale
) {

    HttpSession session = request.getSession(false);

    if (session == null
            || session.getAttribute("actorName") == null) {

        return ResponseEntity.badRequest()
                .body("Session expired. Please login again.");
    }

    try {

        String actorName =
                (String) session.getAttribute("actorName");

        employeeDocumentMailService.sendUploadLink(
                employeeId,
                actorName,
                locale
        );

        return ResponseEntity.ok(
                "Document upload link sent successfully"
        );

    } catch (Exception e) {

        e.printStackTrace();

        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
}
     @GetMapping("/document-upload")
    public String documentUploadPage(
            @RequestParam String token,
            Model model
    ) {

        try {

            HrmsDocumentUploadLink link =
                    linkService.validateToken(token);

            model.addAttribute("token", token);

            model.addAttribute(
                    "employee",
                    link.getEmployeeMaster()
            );

            return "segments/employee-document-upload";

        } catch (Exception e) {

            model.addAttribute(
                    "message",
                    e.getMessage()
            );

            return "invalid-upload-link";
        }
    }
    @PostMapping("/upload-employee-document")
@ResponseBody
public ResponseEntity<?> uploadEmployeeDocument(
        @RequestParam String token,
        @RequestParam String documentType,
        @RequestParam MultipartFile file) {
    
   

    try {
        String message = employeeDocumentUploadService.uploadDocument(
                token,
                documentType,
                file
        );

        return ResponseEntity.ok(message);

    } catch (Exception e) {
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
}
    @GetMapping("/employee-uploaded-documents")
@ResponseBody
public ResponseEntity<?> getUploadedDocuments(
        @RequestParam String token) {

    try {
        HrmsDocumentUploadLink link = linkService.validateToken(token);

        Long employeeId = link.getEmployeeMaster().getId();

        List<HrmsEmployeeDocument> documents =
                employeeDocumentUploadService.getEmployeeDocuments(employeeId);

        List<Map<String, Object>> list = new ArrayList<>();

        for (HrmsEmployeeDocument doc : documents) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", doc.getId());
            map.put("documentType", doc.getDocumentType());
            map.put("fileName", doc.getFileName());
            map.put("status", doc.getStatus());
            map.put("uploadedAt", doc.getUploadedAt());
            map.put("verificationStatus", doc.getVerificationStatus());

            list.add(map);
        }

        return ResponseEntity.ok(list);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
@GetMapping("/employee-uploaded-documents-by-employee")
@ResponseBody
public List<Map<String, Object>> getUploadedDocumentsByEmployee(
        @RequestParam Long employeeId) {

    List<HrmsEmployeeDocument> docs = 
            employeeDocumentUploadService.getEmployeeDocuments(employeeId);

    List<Map<String, Object>> list = new ArrayList<>();

    for (HrmsEmployeeDocument doc : docs) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", doc.getId());
        map.put("documentType", doc.getDocumentType());
        map.put("fileName", doc.getFileName());
        map.put("status", doc.getStatus());
        map.put("verificationStatus", doc.getVerificationStatus());

        list.add(map);
    }

    return list;
}
@GetMapping("/generate-employee-upload-preview-url")
@ResponseBody
public ResponseEntity<?> generateEmployeeUploadPreviewUrl(
        @RequestParam Long documentId) {

    try {
        HrmsEmployeeDocument doc = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        System.out.println("do"+doc.getS3Path());
        String url = s3Service.generatePresignedUrl(doc.getS3Path());
//        String url = "https://hrms-interview-cv.s3.ap-south-1.amazonaws.com/"+doc.getS3Path();
        System.out.println("url"+url);
        return ResponseEntity.ok(url);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

@PostMapping("/verify-uploaded-document")
@ResponseBody
public ResponseEntity<?> verifyUploadedDocument(
        HttpServletRequest request,
        @RequestParam Long documentId,
        @RequestParam String status) {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("actorName") == null) {
        return ResponseEntity.badRequest()
                .body("Session expired. Please login again.");
    }

    try {
        String verifiedBy = (String) session.getAttribute("actorName");

        String message = employeeDocumentUploadService.verifyUploadedDocument(
                documentId,
                status,
                verifiedBy
        );

        return ResponseEntity.ok(message);

    } catch (Exception e) {
        e.printStackTrace();

        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
}

}
