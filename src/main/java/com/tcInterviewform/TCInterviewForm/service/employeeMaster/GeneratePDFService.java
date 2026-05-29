/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.employeeMaster;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeSalary;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 *
 * @author teamd
 */
@Service
public class GeneratePDFService {

    @Autowired
    @Qualifier("emailTemplateEngine")
    private TemplateEngine htmlTemplateEngine;

    public String initTemplate(final String template) {
        if (template.equalsIgnoreCase("offer")) {
            return "html/offerLetterTemplate";
        } else if (template.equalsIgnoreCase("loi")) {
            return "html/loiLetterTemplate";
        } else if (template.equalsIgnoreCase("appointment")) {
            return "html/appointmentLetter";
        } else if (template.equalsIgnoreCase("agreement")) {
            return "html/EmployeeAgreement";
        } else if (template.equalsIgnoreCase("increment")) {
            return "html/incrementLetter";
        } else if (template.equalsIgnoreCase("promotion")) {
            return "html/promotionLetter";
        } else if (template.equalsIgnoreCase("experience")) {
            return "html/experienceLetter";
        }
        else if (template.equalsIgnoreCase("warning")) {
            return "html/warningLetter";
        }
        else if (template.equalsIgnoreCase("nda")) {
            return "html/nda";
        }
        return null;
    }

    public String formatDateWithSuffix(LocalDate date) {
        if (date == null) {
            return "";
        }

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

        return day + suffix + " "
                + date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH)
                + " " + date.getYear();
    }

    @Transactional(rollbackFor = Exception.class)
    ByteArrayOutputStream generateAgreementPdf(HrmsEmployeeMaster emp, HrmsEmployeeSalary salary, String senderName) {
//        System.out.println("in agreement");
        Context context = new Context();

        String baseUrl = this.getClass()
                .getResource("/static/asset/images/")
                .toExternalForm();

        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        String salutation;
        if (emp.getGender().equalsIgnoreCase("Male")) {
            salutation = "Mr.";
        } else {
            salutation = "Ms.";
        }
        context.setVariable("employeeName", salutation + " " + emp.getEmployeeName());
        context.setVariable("employeeId", emp.getEmployeeId());
        context.setVariable("age", Period.between(emp.getDateOfBirth(), LocalDate.now()).getYears());
        context.setVariable("designation", emp.getDesignation());
        context.setVariable("location", emp.getLocation());

        context.setVariable("addr", emp.getEmployeeAddress());
        context.setVariable("city", emp.getCity());
        context.setVariable("state", emp.getState());
        context.setVariable("pincode", emp.getPincode());

        context.setVariable("joiningDate", formatDateWithSuffix(emp.getDateOfJoining()));
        context.setVariable("actorName", senderName);
        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
        String documentId = "TC/AL/" + LocalDate.now().getYear() + "/"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        context.setVariable("docNo", documentId);

        context.setVariable("empSal", salary);
        try {
            String html = htmlTemplateEngine.process(this.initTemplate("agreement"), context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(outputStream);

            builder.run();

            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
        }
    }

    ByteArrayOutputStream generateIncrementPromotionPdf(HrmsEmployeeMaster emp, HrmsEmployeeSalary salary, String senderName, String type, String documentId, String senderRole) {
        Context context = new Context();

        String baseUrl = this.getClass()
                .getResource("/static/asset/images/")
                .toExternalForm();

        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        String salutation;
        if (emp.getGender().equalsIgnoreCase("Male")) {
            salutation = "Mr.";
        } else {
            salutation = "Ms.";
        }
        context.setVariable("employeeName", salutation + " " + emp.getEmployeeName());
        context.setVariable("employeeId", emp.getEmployeeId());
//        context.setVariable("age", Period.between(emp.getDateOfBirth(), LocalDate.now()).getYears());
        context.setVariable("designation", emp.getDesignation());
        context.setVariable("location", emp.getLocation());
        context.setVariable("joiningDate", formatDateWithSuffix(emp.getDateOfJoining()));
        context.setVariable("senderName", senderName);
        String role = null;
        if (senderRole.equalsIgnoreCase("hr")) {
            role = "Human Resource Department";
        } else if (senderRole.equalsIgnoreCase("Director")) {
            role = "Funder | President";
        } else if (senderRole.equalsIgnoreCase("vp")) {
            role = "Vice President";
        }
        context.setVariable("senderRole", role);
        context.setVariable("level", emp.getGrade());
        context.setVariable("department", emp.getDepartment());
        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));

        context.setVariable("docNo", documentId);
        context.setVariable("oldDesignation", emp.getOld_designation());
        context.setVariable("oldlevel", emp.getOld_level());
        context.setVariable("oldsal", emp.getOld_salary());
        context.setVariable("empSal", salary);
        context.setVariable("effectiveFrom", formatDateWithSuffix(salary.getEffectiveFrom()));
        try {
            String html = htmlTemplateEngine.process(this.initTemplate(type), context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(outputStream);

            builder.run();

            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
        }

    }

    ByteArrayOutputStream generateExperiencePdf(HrmsEmployeeMaster emp, String senderName, String documentNo) {
        Context context = new Context();

        String baseUrl = this.getClass()
                .getResource("/static/asset/images/")
                .toExternalForm();

        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        String salutation;
        if (emp.getGender().equalsIgnoreCase("Male")) {
            salutation = "Mr.";
        } else {
            salutation = "Ms.";
        }
        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
        context.setVariable("salutation", salutation);
        context.setVariable("employeeName", emp.getEmployeeName());
        context.setVariable("designation", emp.getDesignation());
        context.setVariable("joiningDate", formatDateWithSuffix(emp.getDateOfJoining()));
        context.setVariable("lastDate", formatDateWithSuffix(emp.getDateOfLeaving()));
        context.setVariable("senderName", senderName);
        context.setVariable("docNo", documentNo);
        try {
            String html = htmlTemplateEngine.process(this.initTemplate("experience"), context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(outputStream);

            builder.run();

            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
        }
    }

    ByteArrayOutputStream generateWarningPdf(HrmsEmployeeMaster emp, String senderName, Context context) {
//         Context context = new Context();

        String baseUrl = this.getClass()
                .getResource("/static/asset/images/")
                .toExternalForm();

        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        String salutation;
        if (emp.getGender().equalsIgnoreCase("Male")) {
            salutation = "Mr.";
        } else {
            salutation = "Ms.";
        }
        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
        context.setVariable("employeeName", salutation + " " + emp.getEmployeeName());
        context.setVariable("senderName", senderName);
        context.setVariable("empId", emp.getEmployeeId());
        context.setVariable("designation", emp.getDesignation());
        context.setVariable("location", emp.getLocation());
        try {
            String html = htmlTemplateEngine.process(this.initTemplate("warning"), context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(outputStream);

            builder.run();

            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
        }
    }
ByteArrayOutputStream generateNDAPdf(HrmsEmployeeMaster emp, String senderName, String documentNo) {
 
        Context context = new Context();

        String baseUrl = this.getClass()
                .getResource("/static/asset/images/")
                .toExternalForm();

        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        String salutation;
        if (emp.getGender().equalsIgnoreCase("Male")) {
            salutation = "Mr.";
        } else {
            salutation = "Ms.";
        }
         context.setVariable("employeeName", salutation + " " + emp.getEmployeeName());
         context.setVariable("employeeId", emp.getEmployeeId());
        context.setVariable("age", Period.between(emp.getDateOfBirth(), LocalDate.now()).getYears());
        context.setVariable("designation", emp.getDesignation());
        context.setVariable("location", emp.getLocation());
        context.setVariable("department", emp.getDepartment());

        context.setVariable("addr", emp.getEmployeeAddress());
        context.setVariable("city", emp.getCity());
        context.setVariable("state", emp.getState());
        context.setVariable("pincode", emp.getPincode());
        context.setVariable("contact", emp.getContactNumber());

        context.setVariable("joiningDate", formatDateWithSuffix(emp.getDateOfJoining()));
        context.setVariable("senderName", senderName);
        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
        String documentId = "TC/NDA/" + LocalDate.now().getYear() + "/"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        context.setVariable("docNo", documentId);
        try {
            String html = htmlTemplateEngine.process(this.initTemplate("nda"), context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(outputStream);

            builder.run();

            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
        }
    }

    ByteArrayOutputStream generateAppointmentPdf(HrmsEmployeeMaster emp, String senderName) {
        Context context = new Context();

        String baseUrl = this.getClass()
                .getResource("/static/asset/images/")
                .toExternalForm();

        context.setVariable("logoPath", baseUrl + "TClogo-final.png");
        context.setVariable("stampPath", baseUrl + "TCRoundStamp.jpg");
        String salutation;
        if (emp.getGender().equalsIgnoreCase("Male")) {
            salutation = "Mr.";
        } else {
            salutation = "Ms.";
        }
         context.setVariable("employeeName", salutation + " " + emp.getEmployeeName());
         context.setVariable("employeeId", emp.getEmployeeId());
        context.setVariable("age", Period.between(emp.getDateOfBirth(), LocalDate.now()).getYears());
        context.setVariable("designation", emp.getDesignation());
        context.setVariable("location", emp.getLocation());
        context.setVariable("department", emp.getDepartment());

        context.setVariable("addr", emp.getEmployeeAddress());
        context.setVariable("city", emp.getCity());
        context.setVariable("state", emp.getState());
        context.setVariable("pincode", emp.getPincode());
        context.setVariable("contact", emp.getContactNumber());
        context.setVariable("salary", emp.getFinalSalary());

        context.setVariable("joiningDate", formatDateWithSuffix(emp.getDateOfJoining()));
        context.setVariable("actorName", senderName);
        context.setVariable("date", formatDateWithSuffix(LocalDate.now()));
        String documentId = "TC/APL/" + LocalDate.now().getYear() + "/"
                + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        context.setVariable("docNo", documentId);
        try {
            String html = htmlTemplateEngine.process(this.initTemplate("appointment"), context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(outputStream);

            builder.run();

            return outputStream;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed. Transaction rolled back.", e);
        }
    }

    
}
