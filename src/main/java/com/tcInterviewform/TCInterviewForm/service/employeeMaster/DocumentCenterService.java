/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.employeeMaster;

import com.tcInterviewform.TCInterviewForm.communication.service.EmailSender;
import com.tcInterviewform.TCInterviewForm.model.HrmsDocumentCenter;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeSalary;
import com.tcInterviewform.TCInterviewForm.model.LetterIssue.HrmsWarningLetter;
import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.HrmsWarningLetterRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeDocumentCenterRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeSalaryRepository;
import com.tcInterviewform.TCInterviewForm.service.S3Service;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

/**
 *
 * @author teamd
 */
@Service
@RequiredArgsConstructor
public class DocumentCenterService {

    private final HrmsEmployeeDocumentCenterRepository documentRepo;
    private final HrmsEmployeeMasterRepository employeeRepo;
    private final HrmsEmployeeSalaryRepository salaryRepo;
    private final EmailSender emailSender;
    private final GeneratePDFService generatePdfService;
    private final S3Service s3Service;
    private final HrmsWarningLetterRepository warningRepo;

    public Map<String, Long> getEmployeeDocumentCounts(Long employeeId) {

        Map<String, Long> map = new HashMap<>();

        map.put("loi", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "loi", "SENT"));

        map.put("offer", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "offer", "SENT"));

        map.put("increment", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "increment", "SENT"));

        map.put("promotion", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "promotion", "SENT"));

        map.put("exp", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "exp", "SENT"));

        map.put("relieving", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "relieving", "SENT"));

        map.put("noi", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "noi", "SENT"));
        map.put("warning", documentRepo.countByEmployeeMasterIdAndDocumentTypeAndStatus(
                employeeId, "warning", "SENT"));
//        System.out.println("map" + map.toString());
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    public String generateDocument(
            Long employeeId,
            String documentType,
            String senderName,
            String senderRole) throws Exception {

        HrmsEmployeeMaster emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        HrmsEmployeeSalary salary = salaryRepo
                .findTopByEmployeeMasterIdAndIsCurrentOrderByEffectiveFromDesc(employeeId, 1)
                .orElse(null);

        ByteArrayOutputStream pdfStream = null;
        String documentNo = "TC/" + documentType.substring(0, 2).toUpperCase() + "/"
                + LocalDate.now().getYear() + "/"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        switch (documentType.toLowerCase()) {

            case "offer":
                if (salary == null) {
                    throw new RuntimeException("Salary details not found for agreement letter");
                }
//                pdfStream = letterService.generateOfferPdf(emp, salary, senderName);
                break;

            case "appointment":
                pdfStream = generatePdfService.generateAppointmentPdf(emp, senderName);
                break;
            case "agreement":
                pdfStream = generatePdfService.generateAgreementPdf(emp, salary, senderName);
                break;

            case "increment":
                if (salary == null) {
                    throw new RuntimeException("Salary details not found for increment letter");
                }
                pdfStream = generatePdfService.generateIncrementPromotionPdf(emp, salary, senderName, documentType.toLowerCase(), documentNo,senderRole);
                break;

            case "promotion":
                if (salary == null) {
                    throw new RuntimeException("Salary details not found for promotion letter");
                }
                pdfStream = generatePdfService.generateIncrementPromotionPdf(emp, salary, senderName, documentType.toLowerCase(), documentNo,senderRole);
                break;

            case "exp":
                pdfStream = generatePdfService.generateExperiencePdf(emp, senderName, documentNo);
                break;

            case "relieving":
//                pdfStream = letterService.generateRelievingPdf(emp, senderName);
                break;

            case "nda":
                pdfStream = generatePdfService.generateNDAPdf(emp, senderName,documentNo);
                break;
//            case "warning":
//                pdfStream = generatePdfService.generateWarningPdf(emp, senderName, documentNo);
//                break;

            default:
                throw new RuntimeException("Invalid document type: " + documentType);
        }

        String fileName = documentType.substring(0, 3).toUpperCase() + "_"
                + emp.getEmployeeName().replace(" ", "_")
                + "_" + LocalDate.now().getYear() + "_" + UUID.randomUUID().toString().substring(0, 8)
                + ".pdf";

        String folderPath = "D:/hrms_documents/" + documentType + "/";
        String fullPath = folderPath + fileName;
//        System.out.println("pdfStream-" + pdfStream.toByteArray());
//        savePdfToLocal(pdfStream, folderPath, fileName);
        String s3Key = s3Service.uploadPdfToS3(pdfStream, documentType, fileName);
        HrmsDocumentCenter doc = new HrmsDocumentCenter();

        doc.setEmployeeMaster(emp);
        doc.setSalary(salary);
        doc.setDocumentType(documentType);
        doc.setDocumentNo(documentNo);
//        doc.setPdfPath(fullPath);
        doc.setPdfPath(s3Key);
        doc.setStatus("GENERATED");
        doc.setMailStatus("NOT_SENT");
        doc.setGeneratedBy(senderName);
        doc.setGeneratedOn(LocalDateTime.now());

        documentRepo.save(doc);

        return documentType + " PDF generated successfully";
    }

    private void savePdfToLocal(
            ByteArrayOutputStream pdfStream,
            String folderPath,
            String fileName) throws Exception {

        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(folderPath + fileName)) {
            pdfStream.writeTo(fos);
        }
    }

    public Map<String, Object> getEmployeeDocumentData(Long employeeId, String type) {

        HrmsEmployeeMaster emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        System.out.println("emp--"+emp.getGrade());
        List<HrmsDocumentCenter> history
                = documentRepo.findByEmployeeMasterIdAndDocumentTypeOrderByIdDesc(
                        employeeId,
                        type
                );

        Map<String, Object> current = new HashMap<>();

        current.put("employeeMasterId", emp.getId());
        current.put("employeeId", emp.getEmployeeId());
        current.put("employeeName", emp.getEmployeeName());
        current.put("designation", emp.getDesignation());
        current.put("dateOfJoining", emp.getDateOfJoining());
        current.put("level", emp.getGrade());

        if (!history.isEmpty()) {

            HrmsDocumentCenter latest = history.get(0);

            current.put("documentId", latest.getId());
            current.put("status", latest.getStatus());
            current.put("mailStatus", latest.getMailStatus());
            current.put("pdfPath", latest.getPdfPath());
            current.put("documentNo", latest.getDocumentNo());

        } else {

            current.put("documentId", null);
            current.put("status", "PENDING");
            current.put("mailStatus", "NOT_SENT");
            current.put("pdfPath", null);
            current.put("documentNo", null);
        }

        List<Map<String, Object>> historyList = new ArrayList<>();

        for (HrmsDocumentCenter doc : history) {

            Map<String, Object> h = new HashMap<>();

            h.put("id", doc.getId());
            h.put("documentNo", doc.getDocumentNo());
            h.put("documentType", doc.getDocumentType());
            h.put("status", doc.getStatus());
            h.put("mailStatus", doc.getMailStatus());
            h.put("pdfPath", doc.getPdfPath());
            h.put("generatedBy", doc.getGeneratedBy());
            h.put("generatedOn", doc.getGeneratedOn());
            h.put("sentBy", doc.getSentBy());
            h.put("sentOn", doc.getSentOn());

            historyList.add(h);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("current", current);
        response.put("history", historyList);

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public String sendEmployeeDocument(
            Long documentId,
            String senderName,
            Locale locale) throws Exception {
//        System.out.println("documentId" + documentId);
        HrmsDocumentCenter doc = documentRepo.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        HrmsEmployeeMaster emp = doc.getEmployeeMaster();

        if (emp == null) {
            throw new RuntimeException("Employee not found for document");
        }

        if (emp.getEmailId() == null || emp.getEmailId().trim().isEmpty()) {
            throw new RuntimeException("Employee email id not found");
        }

        if (doc.getPdfPath() == null || doc.getPdfPath().trim().isEmpty()) {
            throw new RuntimeException("PDF path not found. Please generate PDF first.");
        }

//        File pdfFile = new File(doc.getPdfPath());
//
//        if (!pdfFile.exists()) {
//            throw new RuntimeException("PDF file not found at: " + doc.getPdfPath());
//        }
//        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
        byte[] pdfBytes = s3Service.getFileFromS3(doc.getPdfPath());
        boolean sent = sendLetterMail(
                doc.getDocumentType(),
                emp,
                locale,
                pdfBytes
        );

        if (!sent) {
            doc.setMailStatus("FAILED");
            doc.setStatus("FAILED");
            documentRepo.save(doc);

            throw new RuntimeException("Email service failed");
        }

        doc.setMailStatus("SENT");
        doc.setStatus("SENT");
        doc.setSentBy(senderName);
        doc.setSentOn(LocalDateTime.now());

        documentRepo.save(doc);

        return doc.getDocumentType() + " sent successfully to " + emp.getEmailId();
    }

    public Boolean sendLetterMail(
            String letterType,
            HrmsEmployeeMaster emp,
            Locale locale,
            byte[] pdfData) {

        try {
            emailSender.sendHrmsLetterMail(
                    letterType,
                    locale,
                    emp,
                    pdfData
            );

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String generatePresignedUrl(String path) {
        return s3Service.generatePresignedUrl(path);
    }

    @Transactional(rollbackFor = Exception.class)
    public String generateWarningLetter(
            Long employeeId,
            String warningType,
            String warningLevel,
            String incidentReported,
            String incidentDateTime,
            String incidentReportedBy,
            String incidentDescription,
            String senderName) throws Exception {

        HrmsEmployeeMaster employee
                = employeeRepo.findById(employeeId)
                        .orElseThrow(()
                                -> new RuntimeException("Employee not found"));

        LocalDateTime incidentDate
                = LocalDateTime.parse(incidentDateTime);

        String subject = "";
        String intro = "";
        String body = "";
        String explanationRequired = "NO";
        Integer explanationHours = 0;
        String documentNo = "TC/" + "WL" + "/"
                + LocalDate.now().getYear() + "/"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        switch (warningType.toLowerCase()) {

            case "admin":

                subject
                        = warningLevel
                        + " Warning Letter – Administrative Negligence";

                intro
                        = "This letter serves as an official warning regarding the administrative issue reported against you.";

                body
                        = "You are advised to treat this matter seriously and ensure such incidents are not repeated. Any future occurrence may lead to further disciplinary action as per company policy.";

                break;

            case "hr":

                subject
                        = warningLevel
                        + " Warning Letter – HR Policy Violation";

                intro
                        = "This letter is being issued as an official warning regarding violation of company HR/office policies.";

                body
                        = "You are hereby instructed to immediately correct your behavior and strictly adhere to company policies. Repetition of such incidents may result in serious disciplinary action, including termination of employment.";

                break;

            case "operation":

                subject
                        = warningLevel
                        + " Warning Letter – Operational Misconduct";

                intro
                        = "This letter is being issued to formally address the operational concern reported against you.";

                body
                        = "You are hereby instructed to strictly adhere to operational guidelines and maintain professional responsibility going forward. Repetition of such incidents may attract strict disciplinary action.";

                break;

            case "quality":

                subject
                        = warningLevel
                        + " Warning Letter – Quality Compliance Concern";

                intro
                        = "This letter serves as an official warning regarding the incident reported against you related to the Quality process and compliance standards. ";

                body
                        = "You are advised to ensure immediate improvement in accuracy, reporting, monitoring, and process adherence.\n"
                        + "\n"
                        + "Failure to demonstrate improvement may lead to further disciplinary action.\n"
                        + "\n"
                        + "You are requested to submit your explanation within [24/48] hours from receipt of this letter.";

                explanationRequired = "YES";

                explanationHours = 48;

                break;
        }

        Context context = new Context();

        context.setVariable("warningSubject", subject);

        context.setVariable("warningIntro", intro);

        context.setVariable("warningBody", body);

        context.setVariable("warningLevel", warningLevel);

        context.setVariable("incidentReported", incidentReported);
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        String formattedIncidentDate
                = incidentDate.format(formatter);
        context.setVariable("incidentDateTime", formattedIncidentDate);

        context.setVariable("incidentReportedBy", incidentReportedBy);

        context.setVariable("incidentDescription", incidentDescription);

        context.setVariable("explanationRequired", explanationRequired);

        context.setVariable("explanationHours", explanationHours);
        context.setVariable("docNo", documentNo);

        ByteArrayOutputStream pdfStream = generatePdfService.generateWarningPdf(employee, senderName, context);

        String fileName
                = "WARNING_"
                + employee.getEmployeeId()
                + "_"
                + System.currentTimeMillis()
                + ".pdf";

//        String s3Key
//                = "hrms-documents/warning/"
//                + fileName;
        String s3Key = s3Service.uploadPdfToS3(pdfStream, "warning", fileName);
        HrmsDocumentCenter doc
                = new HrmsDocumentCenter();

        doc.setEmployeeMaster(employee);
//        doc.setSalary();
        doc.setDocumentType("warning");
        doc.setDocumentNo(documentNo);
        doc.setPdfPath(s3Key);
        doc.setPdfPath(s3Key);
        doc.setStatus("GENERATED");
        doc.setMailStatus("NOT_SENT");
        doc.setGeneratedBy(senderName);
        doc.setGeneratedOn(LocalDateTime.now());

        HrmsDocumentCenter document = documentRepo.save(doc);

        HrmsWarningLetter warning
                = new HrmsWarningLetter();

        warning.setEmployeeMaster(employee);

        warning.setDocumentCenter(document);

        warning.setWarningType(warningType);

        warning.setWarningLevel(warningLevel);

        warning.setIncidentReported(incidentReported);

        warning.setIncidentDatetime(incidentDate);

        warning.setIncidentReportedBy(incidentReportedBy);

        warning.setIncidentDescription(incidentDescription);

        warning.setStatus("GENERATED");

        warning.setPdfPath(s3Key);

        warning.setCreatedBy(senderName);

        warningRepo.save(warning);

        return "Warning letter generated successfully";
    }
    
     @Transactional(rollbackFor = Exception.class)
public String uploadAcceptedDocument(
        Long documentId,
        MultipartFile file,
        String actorName) throws Exception {

    HrmsDocumentCenter doc = documentRepo.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found"));

    HrmsEmployeeMaster emp = doc.getEmployeeMaster();

    String fileName = "ACCEPTED_"
            + doc.getDocumentType().toUpperCase()
            + "_"
            + emp.getEmployeeId()
            + "_"
            + System.currentTimeMillis()
            + "_"
            + file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");

    String s3Key = "hrms-documents/accepted/"
            + doc.getDocumentType()
            + "/"
            + emp.getEmployeeId()
            + "/"
            + fileName;

//    s3Service.uploadFile(file, s3Key);

    doc.setAccepted_pdf_path(actorName);
    doc.setAccepted_uploaded_by(actorName);
    doc.setAccepted_uploaded_on(LocalDateTime.now());
    doc.setAcceptance_status("ACCEPTED_UPLOADED");

    documentRepo.save(doc);

    return "Accepted document uploaded successfully";
}

}
