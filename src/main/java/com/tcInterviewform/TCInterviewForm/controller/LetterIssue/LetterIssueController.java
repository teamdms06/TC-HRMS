/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller.LetterIssue;

import com.tcInterviewform.TCInterviewForm.communication.EmailCollection;
import com.tcInterviewform.TCInterviewForm.model.LetterIssue.EmployeeSalary;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.Loi;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OfferLetter;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OldEmployeeDetails;
import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.IssueLetterMasterRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.LoiRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.OfferLetterRepository;
import com.tcInterviewform.TCInterviewForm.service.LetterIssue.LetterIssueService;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author teamd
 */
@Controller
public class LetterIssueController {

    @Autowired
    LetterIssueService letterService;
    @Autowired
    IssueLetterMasterRepository issueLetterMasterRepo;
//    @Autowired
//    OfferLetterRepository offerLetterRepository;
//    @Autowired
//    LoiRepository loiRepository;

    @GetMapping("getviewLetterById")
    public String getviewLetterById(HttpServletRequest request, HttpServletResponse response, @RequestParam("letterId") int id, @RequestParam("lettertype") String lType, ModelMap model) throws IOException, MessagingException {
        HttpSession session = request.getSession();
//        System.out.println("iddd" + id);
        if (session.isNew()) {
            return "login";
        } else {

            List uniqueLetterDetails = letterService.getLetterUniqueDetails(id);
//            System.out.println("uniqueLetterDetails"+uniqueLetterDetails.toString());
            Object[] row = (Object[]) uniqueLetterDetails.get(0);
            EmployeeSalary empSal = letterService.getEmpSalaryBifircationById(id);
            try {
                model.addAttribute("getAllDetails", uniqueLetterDetails);
                model.addAttribute("jAppId", id);
                model.addAttribute("letterType", lType);
                model.addAttribute("empSal", empSal);
                model.addAttribute("actorName", session.getAttribute("actorName"));
                Date date = new Date(); // current date
                String formatted = letterService.getFormattedDateWithSuffix(date);
                model.addAttribute("date", formatted);
                return "segments/letterIssue/offerLetterTemplate1";

            } catch (Exception e) {
                System.out.println("" + e.getMessage());
                return "";
            }
        }
    }

    @GetMapping("sendMailWithAttachment")
    @ResponseBody
    public String sendMailWithAttachment(HttpServletRequest request, HttpServletResponse response, @RequestParam("letterId") int id, @RequestParam("lettertype") String lType, ModelMap model, Locale locale) throws IOException, MessagingException {
        List uniqueLetterDetails = letterService.getLetterUniqueDetails(id);
        EmployeeSalary empSal = letterService.getEmpSalaryBifircationById(id);
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return "login";
        } else {
            try {
                String senderName = (String) session.getAttribute("actorName");
                byte[] pdf = letterService.generatePdf(uniqueLetterDetails, empSal, senderName).toByteArray();
                Object[] row = (Object[]) uniqueLetterDetails.get(0);
                EmailCollection emailcollect = new EmailCollection();
                emailcollect.setTO(row[4].toString());
                emailcollect.setApplicantName(row[2].toString());
                String acceptUrl = "http://app.the-connections.com/acceptOffer?status=Accept&applicantId=" + row[0];

                Boolean st = letterService.sendAppointmentLetter(locale, emailcollect, pdf, acceptUrl);
                if (st) {
                    String status = "Send";
                    letterService.updateOfferStatus(status, (Integer) row[0]);
                    return "Succuss";
                } else {
                    return "Failed";
                }
            } catch (Exception e) {
                System.out.println("" + e.getMessage());
                return "Failed";
            }
        }
    }

    @GetMapping("/acceptOffer")
    public String acceptOffer(@RequestParam String status, @RequestParam Integer applicantId) {
        letterService.updateOfferStatus(status, applicantId);
        return "defaultSuccess";
    }

//    //for Old employeee 
//    @GetMapping("getOldEmployeeDeatails")
//    public ModelAndView getOldEmployeeDeatails(HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("id") Integer leadId, ModelMap model) {
//        OldEmployeeDetails OldEmp = letterService.getOldEmployeeById(leadId);
//        model.addAttribute("getAllDetails", OldEmp);
//        model.addAttribute("leadId", leadId);
//        return new ModelAndView("segments/letterIssue/oldEmployeeAllData", model);
//    }

//    @GetMapping("getviewOldLetterById")
//    public String getviewOldLetterById(HttpServletRequest request, HttpServletResponse response, @RequestParam("letterId") int id, @RequestParam("lettertype") String lType, ModelMap model) throws IOException, MessagingException {
//        HttpSession session = request.getSession();
////        System.out.println("iddd" + id);
//        if (session.isNew()) {
//            return "login";
//        } else {
//
//            List leadDeatils = letterService.getOldEmployeeDeatails(id);
////            System.out.println("uniqueLetterDetails"+uniqueLetterDetails.toString());
//            Object[] row = (Object[]) leadDeatils.get(0);
//            EmployeeSalary empSal = letterService.getEmpSalaryBifircationById(id);
//            try {
//                model.addAttribute("getAllDetails", leadDeatils);
//                model.addAttribute("jAppId", id);
//                model.addAttribute("letterType", lType);
//                model.addAttribute("empSal", empSal);
//                model.addAttribute("actorName", session.getAttribute("actorName"));
//                Date date = new Date(); // current date
//                String formatted = letterService.getFormattedDateWithSuffix(date);
//                model.addAttribute("date", formatted);
//                return "segments/letterIssue/offerLetterTemplate1";
//
//            } catch (Exception e) {
//                System.out.println("" + e.getMessage());
//                return "";
//            }
//        }
//    }

//    @GetMapping("/sendLOI")
//    @ResponseBody
//    public Map<String, Object> sendLoi(HttpServletRequest request,
//            @RequestParam Integer letterId,
//            @RequestParam String letterType,
//            Locale locale) {
//
//        Map<String, Object> response = new HashMap<>();
//
//        HttpSession session = request.getSession(false);
//
//        if (session == null || session.getAttribute("actorName") == null) {
//            response.put("status", "error");
//            response.put("message", "Session expired. Please login again.");
//            return response;
//        }
//
//        String senderName = (String) session.getAttribute("actorName");
//        List uniqueLetterDetails = letterService.getLetterUniqueDetails(letterId);
//
//        try {
//            ByteArrayOutputStream pdfStream
//                    = letterService.generateLOIPdf(uniqueLetterDetails, senderName);
//
//            byte[] pdf = pdfStream.toByteArray();
//
//            Boolean st = letterService.sendLoiMail(uniqueLetterDetails, locale, pdf);
//            pdfStream.close();
//            if (st) {
//                letterService.updateLOIStatus("Send", letterId);
//
//                response.put("status", "success");
//                response.put("message", "Email sent successfully");
//            } else {
//                issueLetterMasterRepo.deleteByFormId(letterId);
//
//                response.put("status", "error");
//                response.put("message", "Failed to send email");
//            }
//
//        } catch (Exception ex) {
//            issueLetterMasterRepo.deleteByFormId(letterId);
////            System.out.println("ex"+ex.getLocalizedMessage());
//            response.put("status", "error");
//            response.put("message", "Something went wrong: " + ex.getMessage());
//        }
//
//        return response;
//    }
@GetMapping("/sendLetter")
@ResponseBody
public Map<String, Object> sendLetter(
        HttpServletRequest request,
        @RequestParam Integer letterId,
        @RequestParam String letterType,
        Locale locale) {

    Map<String, Object> response = new HashMap<>();

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("actorName") == null) {
        response.put("status", "error");
        response.put("message", "Session expired. Please login again.");
        return response;
    }

    try {
        String senderName = (String) session.getAttribute("actorName");

        boolean sent = letterService.sendLetter(
                letterId,
                letterType,
                senderName,
                locale
        );

        if (sent) {
            response.put("status", "success");
            response.put("message", letterType + " sent successfully");
        } else {
            response.put("status", "error");
            response.put("message", "Failed to send " + letterType);
        }

    } catch (Exception ex) {
        response.put("status", "error");
        response.put("message", "Something went wrong: " + ex.getMessage());
    }

    return response;
}
    //send bulk mail in batch of 10
    
//@PostMapping("/send-bulk-mails")
//public ResponseEntity<?> sendBulkMails(HttpServletRequest request) {
//
//    HttpSession session = request.getSession();
//   
////    if (session == null || session.getAttribute("actorName") == null) {
////        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
////                .body("Session expired. Please login again.");
////    }
//
////    String senderName = (String) session.getAttribute("actorName");
//    String senderName = "Test HR";
//
//    try {
//        letterService.sendBulkEmails(senderName); // ✅ no ids
//    
//        return ResponseEntity.ok("Bulk email process started");
//
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error: " + e.getMessage());
//    }
//}

//@PostMapping("/updateOldEmployeeDetails")
//public String updateOldEmployeeDetails(@ModelAttribute OldEmployeeDetails employee) {
//
//    Boolean status=letterService.updateOldEmployeeDeatials(employee);
////    System.out.println("sttrgh"+status);
//    return   "redirect:/getOldEmployeeDeatails?id=" + employee.getId(); 
//}
//@PostMapping("/send-single-mail")
//public ResponseEntity<?> sendMail(@RequestParam Integer id,
//                                  HttpServletRequest request) {
//
//    HttpSession session = request.getSession();
//
//    String senderName = "Test HR"; // or from session
//
//    try {
//      String status=  letterService.sendSingleEmail(id, senderName);
//          if(status.equalsIgnoreCase("Succuss")){
//              letterService.updateLetterStatus("Send", "old_single_data", id);
//        return ResponseEntity.ok("Email sent successfully to employee ID: " + id);
//          }
//          else{
//              issueLetterMasterRepo.deleteByFormId(id);
//              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error: " + status); 
//          }
//
//    } catch (Exception e) {
//        issueLetterMasterRepo.deleteByFormId(id);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error: " + e.getMessage());
//    }
//}

//@GetMapping("/get-document-data")
//@ResponseBody
//public Map<String, Object> getDocumentData(
//        @RequestParam String type
//) {
//
//    Map<String, Object> response = new HashMap<>();
//
//
//    if (type.equals("offer")) {
//
//        List<OfferLetter> sent =
//                offerLetterRepository.findByStatus("SENT");
//
//        List<OfferLetter> pending =
//                offerLetterRepository.findByStatus("PENDING");
//        response.put("sent", sent);
//        response.put("pending", pending);
//
//    } 
//    else if (type.equals("loi")) {
//
//        List<Loi> sent =
//                loiRepository.findByStatus("SENT");
//
//        List<Loi> pending =
//                loiRepository.findByStatus("PENDING");
//        response.put("sent", sent);
//        response.put("pending", pending);
//
//    } 
//
//    return response;
//}

//@GetMapping("/generatePdf")
//@ResponseBody
//public ResponseEntity<?> generatePdf(HttpServletRequest request,
//        @RequestParam Long id,
//        @RequestParam String type
//) {
// HttpSession session = request.getSession(false);
//
//        if (session == null || session.getAttribute("actorName") == null) {
//            ResponseEntity.badRequest()
//                .body("Session expired. Please login again.");
//        }
//
//        
//    try {
//      String senderName = (String) session.getAttribute("actorName");
//        if (type.equals("offer")) {
//
//            OfferLetter offerLetter =
//                    offerLetterRepository.findById(id)
//                            .orElseThrow(() ->
//                                    new RuntimeException("Offer Letter Not Found"));
//            System.out.println(offerLetter.getEmployeeName());
//ByteArrayOutputStream pdfStream
//                    = letterService.generateOfferLettePdfFromEntity(offerLetter, senderName);
//            // Generate PDF logic here
//            // upload s3
//              String fileName =
//                    "AgreementLetter_" +
//                    offerLetter.getEmployeeName().replace(" ", "_") +
//                    ".pdf";
//
//            String folderPath = "D:/offer_letters/";
//
//            File folder = new File(folderPath);
//
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//
//            FileOutputStream fos =
//                    new FileOutputStream(folderPath + fileName);
//
//            pdfStream.writeTo(fos);
//
//            fos.close();
//   
//
//            return ResponseEntity.ok("Offer Letter Generated");
//
//        }
//        else if (type.equals("loi")) {
//
//    Loi loiLetter =
//            loiRepository.findById(id)
//                    .orElseThrow(() ->
//                            new RuntimeException("LOI Letter Not Found"));
//
//    ByteArrayOutputStream pdfStream =
//            letterService.generateLoiPdfFromEntity(loiLetter, senderName);
//
//    String fileName =
//            "LOI_" +
//            loiLetter.getEmployeeName().replace(" ", "_") +
//            ".pdf";
//
//    String folderPath = "D:/loi_letters/";
//
//    File folder = new File(folderPath);
//
//    if (!folder.exists()) {
//        folder.mkdirs();
//    }
//
//    String fullPath = folderPath + fileName;
//
//    FileOutputStream fos = new FileOutputStream(fullPath);
//
//    pdfStream.writeTo(fos);
//
//    fos.close();
//
//
//    return ResponseEntity.ok("LOI Generated");
//}
//
//        return ResponseEntity.badRequest()
//                .body("Invalid document type");
//
//    } catch (Exception e) {
//
//        e.printStackTrace();
//
//        return ResponseEntity.badRequest()
//                .body("Error while generating PDF");
//    }
//}

}
