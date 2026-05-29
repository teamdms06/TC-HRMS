/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller.employeeMaster;

import com.tcInterviewform.TCInterviewForm.service.employeeMaster.DocumentCenterService;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
public class EmployeeDocumentController {

    @Autowired
    DocumentCenterService documentCenterService;

    @GetMapping("/employee-document-counts")
    @ResponseBody
    public Map<String, Long> getEmployeeDocumentCounts(
            @RequestParam Long employeeId) {

        return documentCenterService.getEmployeeDocumentCounts(employeeId);
    }

    @GetMapping("/get-documentById")
    @ResponseBody
    public Map<String, Object> getDocumentData(
            @RequestParam Long employeeId,
            @RequestParam String type) {

        return documentCenterService.getEmployeeDocumentData(employeeId, type);
    }

    @GetMapping("/generate-employee-document")
    @ResponseBody
    public ResponseEntity<?> generateEmployeeDocument(
            HttpServletRequest request,
            @RequestParam Long employeeId,
            @RequestParam String documentType) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("actorName") == null) {
            return ResponseEntity.badRequest()
                    .body("Session expired. Please login again.");
        }

        try {
            String senderName = (String) session.getAttribute("actorName");
            String role = (String) session.getAttribute("actor_process");

            String message = documentCenterService.generateDocument(
                    employeeId,
                    documentType,
                    senderName,
                    role
            );

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("PDF generation failed: " + e.getMessage());
        }
    }

//    @GetMapping("/preview-local-pdf")
//public ResponseEntity<byte[]> previewLocalPdf(
//        @RequestParam String path
//) {
//
//    try {
//
//        File file = new File(path);
//
//        byte[] pdfBytes =
//                Files.readAllBytes(file.toPath());
//
//        return ResponseEntity.ok()
//                .header(
//                        HttpHeaders.CONTENT_DISPOSITION,
//                        "inline; filename=" + file.getName()
//                )
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(pdfBytes);
//
//    } catch (Exception e) {
//
//        e.printStackTrace();
//
//        return ResponseEntity.badRequest().build();
//    }
//}
    @GetMapping("/generate-preview-url")
    @ResponseBody
    public ResponseEntity<?> generatePreviewUrl(
            @RequestParam String path
    ) {
//  path="employee-documents/TC0070213/pan/pan_1779525030408_OfferLetter_Shahista_Javed_Shaikh.pdf";
        try {

            String url
                    = documentCenterService.generatePresignedUrl(path);
//        System.out.println("url"+url);

            return ResponseEntity.ok(url);

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Failed to generate preview URL");
        }
    }

    @GetMapping("/send-employee-document")
    @ResponseBody
    public ResponseEntity<?> sendEmployeeDocument(
            HttpServletRequest request,
            @RequestParam Long documentId,
            Locale locale) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("actorName") == null) {
            return ResponseEntity.badRequest()
                    .body("Session expired. Please login again.");
        }

        try {
            String senderName = (String) session.getAttribute("actorName");

            String message = documentCenterService.sendEmployeeDocument(
                    documentId,
                    senderName,
                    locale
            );

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Mail sending failed: " + e.getMessage());
        }
    }

    @PostMapping("/generate-warning-letter")
    @ResponseBody
    public ResponseEntity<?> generateWarningLetter(
            HttpServletRequest request,
            @RequestParam Long employeeId,
            @RequestParam String warningType,
            @RequestParam String warningLevel,
            @RequestParam String incidentReported,
            @RequestParam String incidentDateTime,
            @RequestParam String incidentReportedBy,
            @RequestParam String incidentDescription) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("actorName") == null) {
            return ResponseEntity.badRequest()
                    .body("Session expired. Please login again.");
        }

        try {
            String senderName = (String) session.getAttribute("actorName");

            String message = documentCenterService.generateWarningLetter(
                    employeeId,
                    warningType,
                    warningLevel,
                    incidentReported,
                    incidentDateTime,
                    incidentReportedBy,
                    incidentDescription,
                    senderName
            );

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest()
                    .body("Warning letter generation failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/upload-accepted-document")
@ResponseBody
public ResponseEntity<?> uploadAcceptedDocument(
        HttpServletRequest request,
        @RequestParam Long documentId,
        @RequestParam MultipartFile file) {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("actorName") == null) {
        return ResponseEntity.badRequest().body("Session expired");
    }

    try {
        String actorName = (String) session.getAttribute("actorName");

        String msg = documentCenterService.uploadAcceptedDocument(
                documentId,
                file,
                actorName
        );

        return ResponseEntity.ok(msg);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}
