/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.LetterIssue;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.tcInterviewform.TCInterviewForm.communication.EmailCollection;
import com.tcInterviewform.TCInterviewForm.communication.service.EmailSender;
import com.tcInterviewform.TCInterviewForm.model.LetterIssue.EmployeeSalary;
import com.tcInterviewform.TCInterviewForm.model.LetterIssue.IssueletterMaster;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.Loi;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OfferLetter;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OldEmployeeDetails;
import com.tcInterviewform.TCInterviewForm.repository.JobApplicantRepository;
import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.EmployeeSalaryRepository;
import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.IssueLetterMasterRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.LoiRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.OfferLetterRepository;
//import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.OldEmployeeDetailsRepository;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author teamd
 */
@Service
public class LetterIssueService {

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine htmlTemplateEngine;
    @Autowired
    JobApplicantRepository letterIssueRepo;
    @Autowired
    EmployeeSalaryRepository salBifurRepo;
    @Autowired
    EmailSender emailSender;
//    @Autowired
//    OldEmployeeDetailsRepository oldEmpDeataialRepo;
    @Autowired
    IssueLetterMasterRepository issueLetterMasterRepo;
//    @Autowired
//    OfferLetterRepository offerLetterRepo;
//    @Autowired
//    LoiRepository loiRepo;
    public List getLetterUniqueDetails(Integer lId) {
        return letterIssueRepo.getUniqueletterIssueDetails(lId);
    }

    public ByteArrayOutputStream generatePdf(List employee, EmployeeSalary empSal, String senderName) throws IOException, ParseException {

        Context context = new Context();
        String baseUrl = this.getClass().getResource("/static/asset/images/").toExternalForm();
        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        Object[] row = (Object[]) employee.get(0);
//        context.setVariable("getAllDetails", employee);
        String salutation = "";
        if (row[19] != null) {
            if (row[19].toString().endsWith("Male")) {
                salutation = "Mr.";
            } else if (row[19].toString().endsWith("Female")) {
                salutation = "Mrs.";
            }
        }
//        context.setVariable("salutation", salutation);
        context.setVariable("employeeName", salutation + " " + row[2].toString());
        context.setVariable("age", Period.between(toLocalDate(row[20]), LocalDate.now()).getYears());
        context.setVariable("addr", row[12].toString());
        context.setVariable("city", row[13].toString());
        context.setVariable("state", row[14].toString());
        context.setVariable("pincode", row[15].toString());
        context.setVariable("designation", row[5].toString());

//        Object dateObj = row[9];
//        LocalDate localDate = null;
//
//        if (dateObj instanceof java.sql.Date) {
//            localDate = ((java.sql.Date) dateObj).toLocalDate();
//        } else if (dateObj instanceof java.util.Date) {
//            localDate = ((java.util.Date) dateObj).toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//        }
        context.setVariable("joiningDate", formatDateWithSuffix(toLocalDate(row[9])));
        context.setVariable("empSal", empSal);
        context.setVariable("actorName", senderName); // current date
        String formatted = formatDateWithSuffix(LocalDate.now());
        context.setVariable("date", formatted);
        String documentId = "TC/OL/" + LocalDate.now().getYear() + "/" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        context.setVariable("docNo", documentId);
        //store doc id in master

        IssueletterMaster master = new IssueletterMaster();
        master.setDocumentId(documentId);
        master.setDocumentName("OL");
        master.setForm("In-Selected");
        master.setFormId((Integer) row[0]);
        master.setIssueBy(senderName);
        issueLetterMaster(master);

//        System.out.println("--" + row[9].toString());
//        String dateStr = (String) row[9]; // "2025-06-20"
//        System.out.println("dateStr" + dateStr);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date jDate = sdf.parse(dateStr);
//        context.setVariable("jdate", getFormattedDateWithSuffix(jDate));
//        String html = htmlTemplateEngine.process("offerLetterTemplate", context);
        final String html = this.htmlTemplateEngine.process(this.initTemplate("offer"), (IContext) context);
//        String html = "<html>Hello0000000000000</html>";
//        System.out.println("HTML OUTPUT:\n" + html);
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        String xhtml = doc.html();
//        return xhtml;
//        System.out.println("Cleaned XHTML:\n" + xhtml); // Debug print

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(xhtml, baseUrl);
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();

        return outputStream;
    }

    public Boolean sendAppointmentLetter(Locale locale, EmailCollection emailcollect, byte[] pdfData, String acceptUrl) throws MessagingException {

        try {
            this.emailSender.sendAppointmentLetter("offerLetterMail", locale, emailcollect, pdfData, acceptUrl);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
//        Object[] row = (Object[]) employee.get(0);
//        String fromEmail = "teamdms1@theconnectionsindia.com";
//        String appPassword = "urzc dbzv iuga eppk";
        //       try {

//            JavaMailSender mailSender = createMailSender(fromEmail, appPassword);
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("teamdms1@theconnectionsindia.com");
//            helper.setTo(row[4].toString());
//            helper.setSubject("THE Connectons- Offer Letter");
////            helper.setText("Please find your offer letter attached. And click the below button for Accept the offer", true);
//
//            String acceptUrl = "http://192.168.114.190:8091/acceptOffer?status=Accept&applicantId=" + row[0];
//
//            String htmlContent
//                    = "<html>"
//                    + "<body style='font-family: Arial, sans-serif; font-size: 14px; color: #333;'>"
//                    + "<p>Dear Candidate,</p>"
//                    + "<p>Please find your <b>Offer Letter</b> attached with this email.</p>"
//                    + "<p>We are excited to have you onboard. Kindly click the button below to accept the offer:</p>"
//                    + "<div style='margin: 20px 0;'>"
//                    + "<a href='" + acceptUrl + "' "
//                    + "style='background-color:#28a745; color:#ffffff; padding:12px 25px; "
//                    + "text-decoration:none; border-radius:5px; font-weight:bold; display:inline-block;'>"
//                    + "Accept Offer</a>"
//                    + "</div>"
//                    + "<p>If you are unable to click the button, please copy and paste the below link into your browser:</p>"
//                    + "<p style='color:#0066cc;'>" + acceptUrl + "</p>"
//                    + "<br>"
//                    + "<p>We look forward to working with you.</p>"
//                    + "<p>Best Regards,<br>"
//                    + "<b>HR Team</b><br>"
//                    + "The-Connections ITES Business Services Pvt. Ltd.</p>"
//                    + "</body></html>";
//
//            helper.setText(htmlContent, true);
//            helper.addAttachment("TC_Offer_Letter.pdf", new ByteArrayResource(pdfData));
//
//            mailSender.send(message);
        //           return "Email sent successfully.";
//        } catch (MessagingException e) {
//            e.printStackTrace(); // Or use a logger
//            return "Failed to send email: " + e.getMessage();
//        }
    }

    public String initTemplate(final String template) {
        if (template.equalsIgnoreCase("offer")) {
            return "html/offerLetterTemplate";
        } 
        else if (template.equalsIgnoreCase("loi")) {
            return "html/loiLetterTemplate";
        }
        else if (template.equalsIgnoreCase("appointment")) {
            return "html/appointmentLetter";
        }
        return null;
    }

    public String getFormattedDateWithSuffix(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String suffix;
        if (day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
                    break;
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(" MMM yyyy");
        return day + suffix + sdf.format(date); // e.g., 26th Jun 2025
    }

//    public JavaMailSender createMailSender(String username, String password) {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(465);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//        mailSender.setProtocol("smtps");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.ssl.enable", "true");
//        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
    public EmployeeSalary getEmpSalaryBifircationById(int id) {

        return salBifurRepo.findTopByJobappIdOrderByIdDesc(id);
    }

    public void updateOfferStatus(String status, Integer applicantId) {
        letterIssueRepo.updateOfferStatus(status, applicantId);
    }

//    public List getOldEmployeeDeatails(Integer leadId) {
//        return oldEmpDeataialRepo.getOldEmployeeDeatailsById(leadId);
//    }

//    public OldEmployeeDetails findOldEmployeeDetailsById(Long id) {
//        return oldEmpDeataialRepo.findById(id).orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
//    }

//    public ByteArrayOutputStream generateLOIPdf(OldEmployeeDetails emp) throws IOException, ParseException {
    public ByteArrayOutputStream generateLOIPdf(List employee, String senderName,String templateType) throws IOException, ParseException {
//        System.out.println("in ge pdf");
        Context context = new Context();
        ClassPathResource logo = new ClassPathResource("/static/asset/images/TClogo-final.png");
        String logoPath = logo.getFile().toURI().toString(); // file:///...
        context.setVariable("logoPath", logoPath);
        ClassPathResource tcStamp = new ClassPathResource("/static/asset/images/TCRoundStamp.jpg");
        String tcStampPath = tcStamp.getFile().toURI().toString(); // file:///...
        context.setVariable("stampPath", tcStampPath);

        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
        context.setVariable("actorName", senderName);
        Object[] row = (Object[]) employee.get(0);
        context.setVariable("employeeName", row[2].toString());
        context.setVariable("employeeFirstName", row[2].toString().split(" ")[0]);
        context.setVariable("designation", row[5].toString());
        context.setVariable("department", row[18].toString());
        context.setVariable("location", row[17].toString());
        
       context.setVariable("addr", row[12].toString());
        context.setVariable("city", row[13].toString());
        context.setVariable("state", row[14].toString());
        context.setVariable("pincode", row[15].toString());
//        Object dateObj = row[9];
//        LocalDate localDate = null;
//
//        if (dateObj instanceof java.sql.Date) {
//            localDate = ((java.sql.Date) dateObj).toLocalDate();
//        } else if (dateObj instanceof java.util.Date) {
//            localDate = ((java.util.Date) dateObj).toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//        }

//        System.out.println("loc Date " + localDate);
        context.setVariable("joiningDate", formatDateWithSuffix(toLocalDate(row[9])));
        context.setVariable("salary", row[8].toString());
        String docPrefix=null;
        String docName=null;
        if(templateType.equalsIgnoreCase("loi")){
            docName="LOI";
            docPrefix="TC/LOI/";
        }else{
            docName="APPOINTMENT_LETTER";
          docPrefix="TC/APPOINTMENT/";  
        }
        String documentId =  docPrefix+ LocalDate.now().getYear() + "/" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        context.setVariable("docNo", documentId);
        //store doc id in master
        IssueletterMaster master = new IssueletterMaster();
        master.setDocumentId(documentId);
        master.setDocumentName(docName);
        master.setForm("In-Selected");
        master.setFormId((Integer) row[0]);
        master.setIssueBy(senderName);
        issueLetterMaster(master);
        final String html = this.htmlTemplateEngine.process(this.initTemplate(templateType), (IContext) context);
//        final String html = this.htmlTemplateEngine.process(this.initTemplate("loi"), (IContext) context);
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        String xhtml = doc.html();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        return outputStream;
    }

    public Boolean sendLoiMail(List employee, Locale locale, byte[] pdfData) {
//        System.out.println("in mail" + employe);
        try {

            this.emailSender.sendLoiMail("loi", locale, employee, pdfData);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private void issueLetterMaster(IssueletterMaster master) {
        issueLetterMasterRepo.save(master);
    }

    public void updateLOIStatus(String status, Integer letterId) {
        letterIssueRepo.updateLOIStatus(status, letterId);
    }

    public static String formatDateWithSuffix(LocalDate date) {
        int day = date.getDayOfMonth();
        String suffix;

        if (day >= 11 && day <= 13) {
            suffix = "th";
        } else {
            switch (day % 10) {
                case 1:
                    suffix = "st";
                    break;
                case 2:
                    suffix = "nd";
                    break;
                case 3:
                    suffix = "rd";
                    break;
                default:
                    suffix = "th";
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return day + suffix + " " + date.format(formatter);
    }

//    public void sendBulkEmails(String senderName) {
//
//        List<OldEmployeeDetails> employees = oldEmpDeataialRepo.findAll();
//        System.out.println("employees list " + employees.size());
//        long startTime = System.currentTimeMillis();
//        int batchSize = 5;
//
//        // ✅ Thread pool
//        ExecutorService executor = Executors.newFixedThreadPool(2);
//
//        for (int i = 0; i < employees.size(); i += batchSize) {
//
//            List<OldEmployeeDetails> batch
//                    = employees.subList(i, Math.min(i + batchSize, employees.size()));
//
//            for (OldEmployeeDetails emp : batch) {
//
//                executor.submit(() -> {
//
//                    System.out.println("Processing emp " + emp.getId());
//
//                    try (ByteArrayOutputStream pdfStream
//                            = generatePdfFromEntity(emp, senderName)) {
//
//                        System.out.println("PDF generated for " + emp.getId());
//
//                        String status = emailSender.sendBulkMail(
//                                emp,
//                                pdfStream.toByteArray()
//                        );
//                        Thread.sleep(400);
//                        System.out.println("status " + status);
//                        long empEnd = System.currentTimeMillis();
//                        System.out.println(" Time: " + (empEnd - startTime) / 1000 + " sec");
//                        // saveEmailLog(emp, status ? "SUCCESS" : "FAILED", null);
//
//                    } catch (Exception e) {
//
//                        System.out.println("Error for " + emp.getId() + ": " + e.getMessage());
//                        e.printStackTrace();
//
//                        // saveEmailLog(emp, "FAILED", e.getMessage());
//                    }
//
//                });
//            }
//
//            // ✅ small delay between batches (important)
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        // ✅ shutdown thread pool
//        executor.shutdown();
//
//        try {
//            executor.awaitTermination(1, TimeUnit.HOURS);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }

//    public ByteArrayOutputStream generatePdfFromEntity(OldEmployeeDetails emp, String senderName) throws Exception {
//
//
//        Context context = new Context();
//
//        // ✅ Base path for images
//        String baseUrl = this.getClass()
//                .getResource("/static/asset/images/")
//                .toExternalForm();
//
//        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
//        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
//
//        // ✅ Employee details
//        context.setVariable("employeeName", emp.getEmployeeName());
//        context.setVariable("designation", emp.getDesignation());
//        context.setVariable("location", emp.getLocation());
//
//        context.setVariable("addr", emp.getEmployeeAddress());
//        context.setVariable("city", emp.getCity());
//        context.setVariable("state", emp.getState());
//        context.setVariable("pincode", emp.getPincode());
//
//        // ✅ Date conversion
////        Object dateObj = emp.getDateOfJoining();
////        LocalDate localDate = null;
////
////        if (dateObj instanceof java.sql.Date) {
////            localDate = ((java.sql.Date) dateObj).toLocalDate();
////        } else if (dateObj instanceof java.util.Date) {
////            localDate = ((java.util.Date) dateObj).toInstant()
////                    .atZone(ZoneId.systemDefault())
////                    .toLocalDate();
////        }
////        else {
////            localDate = emp.getDateOfJoining();
////        }
//
//        context.setVariable("joiningDate", formatDateWithSuffix(toLocalDate(emp.getDateOfJoining())));
//
//        // ✅ Other variables
//        context.setVariable("actorName", senderName);
//        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
//
//        // ✅ Document ID
//        String documentId = "TC/OL/" + LocalDate.now().getYear() + "/"
//                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
//
//        context.setVariable("docNo", documentId);
//
//        // ✅ Salary mapping
//        EmployeeSalary empSal = new EmployeeSalary();
//        empSal.setCtc(emp.getCtc());
//        empSal.setBasic(emp.getBasic());
//        empSal.setHra(emp.getHra());
//        empSal.setConveyance(emp.getConveyance());
//        empSal.setMedical(emp.getMedical());
//        empSal.setSpecialAllowance(emp.getSpecialAllowance());
//        empSal.setGrossSalary(emp.getGrossSalary());
//        empSal.setEmployeePf(emp.getEmployeePf());
//        empSal.setProfessionalTax(emp.getProfessionalTax());
//        empSal.setInsuranceDeduction(emp.getInsuranceDeduction());
//        empSal.setNetSalary(emp.getNetSalary());
//
//        context.setVariable("empSal", empSal);
//
//        // ✅ Save document
//        IssueletterMaster master = new IssueletterMaster();
//        master.setDocumentId(documentId);
//        master.setDocumentName("OL");
//        master.setForm("Bulk-Mail");
//        master.setFormId(emp.getId().intValue());
//        master.setIssueBy(senderName);
//        issueLetterMaster(master);
//
//        // ✅ Generate HTML
//        String html = htmlTemplateEngine.process(this.initTemplate("offer"), context);
//
//        // 🚀 Generate PDF using OpenHTMLtoPDF
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        PdfRendererBuilder builder = new PdfRendererBuilder();
//
//        builder.useFastMode(); // ⚡ performance boost
//        builder.withHtmlContent(html, baseUrl);
//        builder.toStream(outputStream);
//
//        builder.run(); // 🔥 create PDF
//
//        return outputStream;
//    }
//
//    public OldEmployeeDetails getOldEmployeeById(Integer leadId) {
//        return oldEmpDeataialRepo.findOldEmployeeByIdId(leadId);
//    }
//
//    public Boolean updateOldEmployeeDeatials(OldEmployeeDetails employee) {
//        OldEmployeeDetails existing = oldEmpDeataialRepo.findById(employee.getId())
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//        try {
//            // 🔥 Update all fields
//            existing.setEmployeeId(employee.getEmployeeId());
//            existing.setEmployeeName(employee.getEmployeeName());
//            existing.setGender(employee.getGender());
//            existing.setDateOfBirth(employee.getDateOfBirth());
//            existing.setDateOfJoining(employee.getDateOfJoining());
////        existing.setLocation(employee.getLocation());
////        existing.setLevels(employee.getLevels());
//            existing.setDesignation(employee.getDesignation());
//            existing.setEmailId(employee.getEmailId());
//            existing.setEmployeeAddress(employee.getEmployeeAddress());
//            existing.setContactNumber(employee.getContactNumber());
//            existing.setCity(employee.getCity());
//            existing.setState(employee.getState());
//            existing.setPincode(employee.getPincode());
//
//            // Salary fields
//            existing.setCtc(employee.getCtc());
//            existing.setBasic(employee.getBasic());
//            existing.setHra(employee.getHra());
//            existing.setConveyance(employee.getConveyance());
//            existing.setMedical(employee.getMedical());
//            existing.setSpecialAllowance(employee.getSpecialAllowance());
//            existing.setGrossSalary(employee.getGrossSalary());
//            existing.setEmployeePf(employee.getEmployeePf());
//            existing.setEmployerPf(employee.getEmployerPf());
//            existing.setProfessionalTax(employee.getProfessionalTax());
//            existing.setInsuranceDeduction(employee.getInsuranceDeduction());
//            existing.setTotalDeduction(employee.getTotalDeduction());
//            existing.setNetSalary(employee.getNetSalary());
//
//            oldEmpDeataialRepo.save(existing);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String sendSingleEmail(Integer id, String senderName) {
//        OldEmployeeDetails emp = oldEmpDeataialRepo.findOldEmployeeByIdId(id);
//        try (ByteArrayOutputStream pdfStream
//                = generatePdfFromEntity(emp, senderName)) {
//
//            System.out.println("PDF generated for " + emp.getId());
//
//            String status = emailSender.sendBulkMail(
//                    emp,
//                    pdfStream.toByteArray()
//            );
//            return status;
//            // saveEmailLog(emp, status ? "SUCCESS" : "FAILED", null);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//            return "Failed to send email:"+e.getMessage();
//
//            // saveEmailLog(emp, "FAILED", e.getMessage());
//        }
//    }

//    public void updateLetterStatus(String status, String formType, Integer id) {
//        oldEmpDeataialRepo.updateOLStatus(id,status);
//    }
    
    
    
//      @Transactional(rollbackFor = Exception.class)
//    public ByteArrayOutputStream generateOfferLettePdfFromEntity(
//            OfferLetter emp,
//            String senderName
//    ) throws Exception {
//
//        Context context = new Context();
//
//        String baseUrl = this.getClass()
//                .getResource("/static/asset/images/")
//                .toExternalForm();
//
//        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
//        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
//
//        context.setVariable("employeeName", emp.getEmployeeName());
//        context.setVariable("age", Period.between(emp.getDateOfBirth(), LocalDate.now()).getYears());
//        context.setVariable("designation", emp.getDesignation());
//        context.setVariable("location", emp.getWorkLocation());
//
//        context.setVariable("addr", emp.getEmployeeAddress());
//        context.setVariable("city", emp.getCity());
//        context.setVariable("state", emp.getState());
//        context.setVariable("pincode", emp.getPincode());
//
//        LocalDate localDate = null;
//        Object dateObj = emp.getDateOfJoining();
//
//        if (dateObj instanceof java.sql.Date) {
//            localDate = ((java.sql.Date) dateObj).toLocalDate();
//        } else if (dateObj instanceof java.util.Date) {
//            localDate = ((java.util.Date) dateObj).toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//        } else {
//            localDate = emp.getDateOfJoining();
//        }
//
//        context.setVariable("joiningDate", formatDateWithSuffix(localDate));
//        context.setVariable("actorName", senderName);
//        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
//
//        String documentId = "TC/OL/" + LocalDate.now().getYear() + "/"
//                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
//
//        context.setVariable("docNo", documentId);
//
//        EmployeeSalary empSal = new EmployeeSalary();
//        empSal.setCtc(emp.getCtc());
//        empSal.setBasic(emp.getBasic());
//        empSal.setHra(emp.getHra());
//        empSal.setConveyance(emp.getConveyance());
//        empSal.setMedical(emp.getMedical());
//        empSal.setSpecialAllowance(emp.getSpecialAllowance());
//        empSal.setGrossSalary(emp.getGrossSalary());
//        empSal.setEmployeePf(emp.getEmployeePf());
//        empSal.setProfessionalTax(emp.getProfessionalTax());
//        empSal.setInsuranceDeduction(emp.getInsuranceDeduction());
//        empSal.setNetSalary(emp.getNetSalary());
//
//        context.setVariable("empSal", empSal);
//
//        /*
//         * IMPORTANT:
//         * Keep DB save before PDF generation only if rollback is needed
//         * when PDF fails.
//         */
//
//        IssueletterMaster master = new IssueletterMaster();
//        master.setDocumentId(documentId);
//        master.setDocumentName("OL");
//        master.setForm("Bulk-Mail");
//        master.setFormId(emp.getId().intValue());
//        master.setIssueBy(senderName);
//
//        issueLetterMaster(master);
//
//        emp.setGenerateBy(senderName);
//        emp.setGenerateOn(new Date());
//        emp.setDocumentId(documentId);
//        emp.setUrl("D:/offer_letters/AgreementLetter_"
//                + emp.getEmployeeName().replace(" ", "_") + ".pdf");
//
//        offerLetterRepo.save(emp);
//
//        try {
//            String html = htmlTemplateEngine.process(this.initTemplate("offer"), context);
//
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.useFastMode();
//            builder.withHtmlContent(html, baseUrl);
//            builder.toStream(outputStream);
//
//            builder.run();
//
//            return outputStream;
//
//        } catch (Exception e) {
//            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
//        }
//    }
// public ByteArrayOutputStream generateLoiPdfFromEntity(Loi loiLetter, String senderName) throws IOException, ParseException {
////        System.out.println("in ge pdf");
//        Context context = new Context();
//        ClassPathResource logo = new ClassPathResource("/static/asset/images/TClogo-final.png");
//        String logoPath = logo.getFile().toURI().toString(); // file:///...
//        context.setVariable("logoPath", logoPath);
//        ClassPathResource tcStamp = new ClassPathResource("/static/asset/images/TCRoundStamp.jpg");
//        String tcStampPath = tcStamp.getFile().toURI().toString(); // file:///...
//        context.setVariable("stampPath", tcStampPath);
//
//        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
//        context.setVariable("actorName", senderName);
//        context.setVariable("employeeName", loiLetter.getEmployeeName());
//        context.setVariable("employeeFirstName", loiLetter.getEmployeeName().split(" ")[0]);
//        context.setVariable("designation", loiLetter.getDesignation());
//        context.setVariable("department", loiLetter.getDepartment());
//        context.setVariable("location", loiLetter.getWorkLocation());
//     
//
////        System.out.println("loc Date " + localDate);
//        context.setVariable("joiningDate", loiLetter.getDateOfJoining());
//        context.setVariable("salary", loiLetter.getOfferedSalaray());
//        String documentId = "TC/LOI/" + LocalDate.now().getYear() + "/" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
//        context.setVariable("docNo", documentId);
//        //store doc id in master
//        IssueletterMaster master = new IssueletterMaster();
//        master.setDocumentId(documentId);
//        master.setDocumentName("LOI");
//        master.setForm("In-Selected");
//        master.setFormId(loiLetter.getId().intValue());
//        master.setIssueBy(senderName);
//        issueLetterMaster(master);
//        
//        //updte doc id generated date and gerenated by
//    loiLetter.setGenerateBy(senderName);
//    loiLetter.setGenerateOn(new Date());
//    loiLetter.setDocumentId(documentId);
//    loiLetter.setUrl("D:/loi_letters/LOI_"+loiLetter.getEmployeeName().replace(" ", "_") +".pdf");
//    loiRepo.save(loiLetter);
//        final String html = this.htmlTemplateEngine.process(this.initTemplate("loi"), (IContext) context);
//        org.jsoup.nodes.Document doc = Jsoup.parse(html);
//        doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
//        String xhtml = doc.html();
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(html);
//        renderer.layout();
//        renderer.createPDF(outputStream);
//
//        return outputStream;
//    }
 
  public  LocalDate toLocalDate(Object dateObj) {

        if (dateObj == null) {
            return null;
        }

        // LocalDate
        if (dateObj instanceof LocalDate) {
            return (LocalDate) dateObj;
        }

        // java.sql.Date
        if (dateObj instanceof java.sql.Date) {
            return ((java.sql.Date) dateObj).toLocalDate();
        }

        // Timestamp
        if (dateObj instanceof Timestamp) {
            return ((Timestamp) dateObj)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        // java.util.Date
        if (dateObj instanceof java.util.Date) {
            return ((java.util.Date) dateObj)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        throw new IllegalArgumentException(
                "Unsupported date type: " + dateObj.getClass()
        );
    }
@Transactional(rollbackFor = Exception.class)
    public String generateAllDocWisePdf(Long id, String type, String senderName) throws Exception {

        switch (type.toLowerCase()) {

//            case "offer":
//                return generateOfferPdf(id, senderName);

            case "loi":
                return generateLoiPdf(id, senderName);

            case "increment":
            case "promotion":
                return generateIncrementPromotionPdf(id, senderName, type);

            default:
                throw new RuntimeException("Invalid document type: " + type);
        }
    }

//    private String generateOfferPdf(Long id, String senderName) throws Exception {
//        OfferLetter offerLetter
//                = offerLetterRepo.findById(id)
//                        .orElseThrow(()
//                                -> new RuntimeException("Offer Letter Not Found"));
//        System.out.println(offerLetter.getEmployeeName());
//        ByteArrayOutputStream pdfStream = generateOfferLettePdfFromEntity(offerLetter, senderName);
//        String fileName =
//                    "AgreementLetter_" + offerLetter.getEmployeeName().replace(" ", "_") +
//                    ".pdf";
//        String folderPath = "D:/agreement_letters/";
//        savePdfToLocal(pdfStream,folderPath,fileName);
//        return "Offer Letter Generated Successfully";
//    }

    private String generateLoiPdf(Long id, String senderName) throws Exception {
        // loi PDF logic
        return "LOI Generated Successfully";
    }

    private String generateIncrementPromotionPdf(Long id, String senderName, String type) throws Exception {
        // increment/promotion PDF logic
        return "Increment/Promotion Letter Generated Successfully";
    }
    
    
    private void savePdfToLocal(
        ByteArrayOutputStream pdfStream,
        String folderPath,
        String fileName
) throws Exception {

    File folder = new File(folderPath);

    if (!folder.exists()) {
        folder.mkdirs();
    }

    try (FileOutputStream fos = new FileOutputStream(folderPath + fileName)) {
        pdfStream.writeTo(fos);
    }
}

    @Transactional(rollbackFor = Exception.class)
    public boolean sendLetter(
            Integer letterId,
            String letterType,
            String senderName,
            Locale locale) throws Exception {

        switch (letterType.toLowerCase()) {

            case "loi":
                return sendLoi(letterId, senderName, locale);

            case "appointment":
                return sendAppointment(letterId, senderName, locale);

            default:
                throw new RuntimeException("Invalid letter type: " + letterType);
        }
    }
    private boolean sendLoi(
            Integer letterId,
            String senderName,
            Locale locale) throws Exception {

        List uniqueLetterDetails =
                letterIssueRepo.getUniqueletterIssueDetails(letterId);

        ByteArrayOutputStream pdfStream =generateLOIPdf(uniqueLetterDetails, senderName,"loi");

        byte[] pdf = pdfStream.toByteArray();
        pdfStream.close();

        boolean sent =sendLetterMail("loi",uniqueLetterDetails, locale, pdf);

        if (sent) {updateLOIStatus("Send", letterId);
            return true;
        }

        issueLetterMasterRepo.deleteByFormId(letterId);
        return false;
    }

    private boolean sendAppointment(
            Integer letterId,
            String senderName,
            Locale locale) throws Exception {

        List uniqueLetterDetails =letterIssueRepo.getUniqueletterIssueDetails(letterId);

        ByteArrayOutputStream pdfStream =
                generateLOIPdf(uniqueLetterDetails, senderName,"appointment");

        byte[] pdf = pdfStream.toByteArray();
        pdfStream.close();

        boolean sent =
                sendLetterMail("appointment",uniqueLetterDetails, locale, pdf);

        if (sent) {
//           updateAppointmentStatus("Send", letterId);
            return true;
        }

        issueLetterMasterRepo.deleteByFormId(letterId);
        return false;
    }
    
    public Boolean sendLetterMail(
        String letterType,
        List employee,
        Locale locale,
        byte[] pdfData
) {
        System.out.println("letterType"+letterType);
    try {
        this.emailSender.sendLoiMail(
                letterType,
                locale,
                employee,
                pdfData
        );

        return true;

    } catch (Exception ex) {
        ex.printStackTrace();
        return false;
    }
}
}
