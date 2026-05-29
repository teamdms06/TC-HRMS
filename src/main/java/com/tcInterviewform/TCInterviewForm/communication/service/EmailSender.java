/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.communication.service;

import com.tcInterviewform.TCInterviewForm.communication.EmailCollection;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OldEmployeeDetails;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

/**
 *
 * @author SIF
 */
@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;
    private String[] tooMail;
    private String[] bcc;
    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine htmlTemplateEngine;

    public void sendSimpleMail(String template, Locale locale, EmailCollection emailcollect, MultipartFile file) throws MessagingException {
        final Context ctx = this.setContextVariables(new Context(locale), emailcollect);
        ctx.setVariable("emailData", (Object) emailcollect);
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(emailcollect.getSUBJECT());
        this.createEmailList(emailcollect.getTO(), emailcollect.getCC(), "", template);
        this.tooMail = StringUtils.trimArrayElements(this.tooMail);
        message.setFrom("hr@the-connections.com");

        try {
            message.setTo(this.tooMail);
            if (template.equalsIgnoreCase("hrMail")) {
                message.addAttachment(file.getOriginalFilename(), file);
            }
        } catch (Exception ex) {
            this.tooMail = new String[1];
            ex.printStackTrace();
            this.tooMail[0] = "hr@the-connections.com";
            message.setTo(this.tooMail);

        }
        final String htmlContent = this.htmlTemplateEngine.process(this.initTemplate(template), (IContext) ctx);
        message.setText(htmlContent, true);
        this.mailSender.send(mimeMessage);
    }

    private Context setContextVariables(Context context, EmailCollection emailcollect) {
        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDate date1 = currentTime.toLocalDate();

        context.setVariable("Date", (Object) date1.toString());
        context.setVariable("applicantName", (Object) emailcollect.getApplicantName());
        context.setVariable("phoneNo", (Object) emailcollect.getaPhoneNo());
        context.setVariable("email", (Object) emailcollect.getaEmailId());
        context.setVariable("gender", (Object) emailcollect.getGender());
        context.setVariable("addr", (Object) emailcollect.getAddr());
        context.setVariable("city", (Object) emailcollect.getCity());
        context.setVariable("state", (Object) emailcollect.getState());
        context.setVariable("reffBy", (Object) emailcollect.getRefferBy());
        context.setVariable("expFresher", (Object) emailcollect.getExpFresher());
        context.setVariable("applyFor", (Object) emailcollect.getApplyFor());

        return context;

    }

    public void createEmailList(final String to, final String cc, final String bcc, final String type) {
//        System.out.println("====================Create Email list========================");
//        System.out.println("cccccccc" + cc);
        final String from = "hr@the-connections.com";
        final String[] tooLst = new String[50];
        String[] ccList = new String[50];
        if (cc != null) {
            ccList = cc.split(",");
            System.out.println("After split cc::");
        }
        if (to.length() > 0) {
            this.tooMail = to.split(",");
//            System.out.println("This too mail" + this.tooMail.length);
//            System.out.println("if To is found " + this.tooMail[0]);
//            System.out.println(to.length());
        } else {
//            System.out.println("Email Found here");
            this.tooMail = StringUtils.split(to, ",");
        }
//        System.out.println("========================Ends Create List EmailSender===============================");
    }

    public String initTemplate(final String template) {
        if (template.equalsIgnoreCase("hrMail")) {
            return "html/hrNotificationmail";
        } else if (template.equalsIgnoreCase("nxtRoundMail")) {
            return "html/NxtRoundNotificationmail";
        } else if (template.equalsIgnoreCase("formLinkMail")) {
            return "html/interviewLinkPage";
        } else if (template.equalsIgnoreCase("offerLetterMail")) {
            return "html/offerLettermail";
        } else if (template.equalsIgnoreCase("loi")) {
            return "html/mailBody/loiMail";
        } 
        else if (template.equalsIgnoreCase("appointment")) {
            return "html/mailBody/appointmentMailBody";
        }
        else if (template.equalsIgnoreCase("hr-document")) {
            return "html/mailBody/hr-document-mail";
        }
        return null;
    }

    public void sendMail(String template, Locale locale, EmailCollection emailcollect) throws MessagingException {
        final Context ctx = this.setContextVariables(new Context(locale), emailcollect);
        ctx.setVariable("emailData", (Object) emailcollect);
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject(emailcollect.getSUBJECT());
        this.createEmailList(emailcollect.getTO(), emailcollect.getCC(), "", template);
        this.tooMail = StringUtils.trimArrayElements(this.tooMail);
        message.setFrom("hr@the-connections.com");

        try {
            message.setTo(this.tooMail);
            if (template.equalsIgnoreCase("hrMail")) {
            }
        } catch (Exception ex) {
            this.tooMail = new String[1];
            ex.printStackTrace();
            this.tooMail[0] = "hr@the-connections.com";
            message.setTo(this.tooMail);

        }
        final String htmlContent = this.htmlTemplateEngine.process(this.initTemplate(template), (IContext) ctx);
        message.setText(htmlContent, true);
        this.mailSender.send(mimeMessage);
    }

    public void sendAppointmentLetter(String template, Locale locale, EmailCollection emailcollect, byte[] pdfData, String acceptUrl) throws MessagingException {

        final Context ctx = this.setContextVariables(new Context(locale), emailcollect);

        ctx.setVariable("acceptUrl", acceptUrl);

        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setSubject("The Connectons- Offer Letter");
//        System.out.println("-" + emailcollect.getApplicantName());
        this.createEmailList(emailcollect.getTO(), emailcollect.getCC(), "", template);
        this.tooMail = StringUtils.trimArrayElements(this.tooMail);
        message.setFrom("hr@the-connections.com");

        try {
            message.setTo(this.tooMail);
            message.addAttachment("TC_Offer_Letter.pdf", new ByteArrayResource(pdfData));

        } catch (Exception ex) {
            this.tooMail = new String[1];
            ex.printStackTrace();
            this.tooMail[0] = "hr@the-connections.com";
            message.setTo(this.tooMail);

        }
        final String htmlContent = this.htmlTemplateEngine.process(this.initTemplate(template), (IContext) ctx);
        message.setText(htmlContent, true);
        this.mailSender.send(mimeMessage);

    }

    public void sendLoiMail(String template, Locale locale, List emp, byte[] pdfData) throws MessagingException {
//        System.out.println("in send"+template);
        final Context ctx = new Context();
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Object[] row = (Object[]) emp.get(0);
        String toMail = row[4].toString();
        if (template.equalsIgnoreCase("loi")) {
            message.setSubject("TC LOI Letter – " + row[2].toString());
             message.addAttachment("Tc_LOI.pdf", new ByteArrayResource(pdfData));
        } else if (template.equalsIgnoreCase("appointment")) {
            message.setSubject("TC Appointment Letter – " + row[2].toString());
             message.addAttachment("Tc_Appointment_letter.pdf", new ByteArrayResource(pdfData));
        }

        this.createEmailList(toMail, "", "", template);
        this.tooMail = StringUtils.trimArrayElements(this.tooMail);
//        message.setFrom("hr@the-connections.com");
        message.setFrom("teamdms1@theconnectionsindia.com");

        try {
            message.setTo(this.tooMail);
         

        } catch (Exception ex) {
            this.tooMail = new String[1];
            ex.printStackTrace();
            this.tooMail[0] = "hr@the-connections.com";
            message.setTo(this.tooMail);

        }
        final String htmlContent = this.htmlTemplateEngine.process(this.initTemplate(template), ctx);
        message.setText(htmlContent, true);
        this.mailSender.send(mimeMessage);
//        System.out.println("send success");
    }

//    public String sendBulkMail(OldEmployeeDetails emp, byte[] pdfBytes) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo(emp.getEmailId());
//            helper.setSubject("Offer Letter - " + emp.getEmployeeName());
//
//            String body = "<p>Dear " + emp.getEmployeeName() + ",</p>"
//                    + "<p>Please find attached your Offer Letter.</p>"
//                    + "<p>Regards,<br/>HR Team</p>";
//
//            helper.setText(body, true);
//
//            helper.addAttachment(
//                    "Offer_Letter_" + emp.getId() + ".pdf",
//                    new ByteArrayResource(pdfBytes)
//            );
//
//            mailSender.send(message);
//
//            return "Succuss";
//
//        } catch (Exception e) {
////        System.out.println("Mail failed for " + emp.getEmailId() + ": " + e.getMessage());
//            return "Failed to send email: " + e.getLocalizedMessage();
//        }
//    }

    public void sendHrmsLetterMail(
            String letterType,
            Locale locale,
            HrmsEmployeeMaster emp,
            byte[] pdfData) throws MessagingException {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        message.setTo(emp.getEmailId());

        message.setSubject(getSubject(letterType));

        message.setText(getMailBody(letterType, emp), true);

        String fileName = getFileName(letterType, emp);

        message.addAttachment(
                fileName,
                new ByteArrayResource(pdfData)
        );

        this.mailSender.send(mimeMessage);
    }

    private String getSubject(String letterType) {

        switch (letterType.toLowerCase()) {

            case "loi":
                return "Letter of Intent - THE-CONNECTIONS";

            case "offer":
                return "Appointment Letter - THE-CONNECTIONS";
            case "agreement":
                return "Agreement Letter - THE-CONNECTIONS";

            case "increment":
                return "Increment Letter - THE-CONNECTIONS";

            case "promotion":
                return "Promotion Letter - THE-CONNECTIONS";

            case "exp":
                return "Experience Letter - THE-CONNECTIONS";

            case "relieving":
                return "Relieving Letter - THE-CONNECTIONS";

            case "nda":
                return "NDA Letter - THE-CONNECTIONS";
                
            case "warning":
                return "Warning Letter - THE-CONNECTIONS";

            default:
                return "HR Document - THE-CONNECTIONS";
        }
    }

    private String getFileName(String letterType, HrmsEmployeeMaster emp) {

        return letterType.toUpperCase()
                + "_"
                + emp.getEmployeeName().replace(" ", "_")
                + "_" +UUID.randomUUID().toString().substring(0, 4)
                + ".pdf";
    }

    private String getMailBody(String letterType, HrmsEmployeeMaster emp) {

         String title = getDocumentTitle(letterType);

    Context context = new Context();
    context.setVariable("title", title);
    context.setVariable("employeeName", emp.getEmployeeName());
      final String htmlContent = this.htmlTemplateEngine.process(this.initTemplate("hr-document"), context);
    return htmlContent;
    }
    
    private String getDocumentTitle(String letterType) {

    switch (letterType.toLowerCase()) {
        case "loi":
            return "Letter of Intent";
        case "offer":
            return "Appointment Letter";
        case "agreement":
            return "Agreement Letter";
        case "increment":
            return "Increment Letter";
        case "promotion":
            return "Promotion Letter";
        case "exp":
            return "Experience Letter";
        case "relieving":
            return "Relieving Letter";
        case "nda":
            return "NDA Letter";
        case "warning":
            return "Warning Letter";
        default:
            return "HR Document";
    }
}
}
