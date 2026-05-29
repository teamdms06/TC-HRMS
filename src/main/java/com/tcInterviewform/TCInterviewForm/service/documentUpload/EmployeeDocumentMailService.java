/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import java.util.Locale;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author teamd
 */
@Service
public class EmployeeDocumentMailService {

    @Autowired
    private HrmsEmployeeMasterRepository employeeRepo;

    @Autowired
    private EmployeeDocumentLinkService linkService;

    @Autowired
    private JavaMailSender mailSender;

    public void sendUploadLink(
            Long employeeId,
            String actorName,
            Locale locale
    ) throws Exception {

        HrmsEmployeeMaster emp =
                employeeRepo.findById(employeeId)
                        .orElseThrow(() ->
                                new RuntimeException("Employee not found"));

        if (emp.getEmailId() == null
                || emp.getEmailId().trim().isEmpty()) {

            throw new RuntimeException(
                    "Employee email id not found"
            );
        }

        String uploadLink =
                linkService.generateUploadLink(employeeId);
        System.out.println("uploadLink"+uploadLink);

      //  sendMail(emp, uploadLink);
    }

    private void sendMail(
            HrmsEmployeeMaster emp,
            String uploadLink
    ) throws Exception {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true,
                        "UTF-8"
                );

        helper.setTo(emp.getEmailId());

        helper.setSubject(
                "Document Submission Link - THE-CONNECTIONS"
        );

        helper.setText(
                getMailBody(emp, uploadLink),
                true
        );

        mailSender.send(message);
    }

    private String getMailBody(
            HrmsEmployeeMaster emp,
            String uploadLink
    ) {

        return "<div style='font-family:Arial,sans-serif;"
                + "padding:20px;"
                + "background:#f4f7fb;'>"

                + "<div style='max-width:700px;"
                + "margin:auto;"
                + "background:white;"
                + "border-radius:18px;"
                + "overflow:hidden;"
                + "box-shadow:0 10px 25px rgba(0,0,0,0.08);'>"

                + "<div style='background:linear-gradient(135deg,#0d6efd,#102a63);"
                + "padding:30px;"
                + "color:white;'>"

                + "<h2 style='margin:0;'>"
                + "Employee Document Submission"
                + "</h2>"

                + "<p style='margin-top:8px;'>"
                + "THE-CONNECTIONS ITES BUSINESS SERVICES PRIVATE LIMITED"
                + "</p>"

                + "</div>"

                + "<div style='padding:30px;'>"

                + "<p>Dear <b>"
                + emp.getEmployeeName()
                + "</b>,</p>"

                + "<p>"
                + "Please upload your required joining documents using the secure link below."
                + "</p>"

                + "<div style='text-align:center;margin:35px 0;'>"

                + "<a href='" + uploadLink + "' "
                + "style='background:#0d6efd;"
                + "color:white;"
                + "padding:14px 28px;"
                + "border-radius:10px;"
                + "text-decoration:none;"
                + "font-weight:bold;"
                + "display:inline-block;'>"

                + "Upload Documents"

                + "</a>"

                + "</div>"

                + "<h4>Required Documents:</h4>"

                + "<ol style='line-height:1.9;'>"

                + "<li>Aadhar Card</li>"
                + "<li>PAN Card</li>"
                + "<li>Passport</li>"
                + "<li>PF/UAN Account Details</li>"
                + "<li>Local Address Proof</li>"
                + "<li>Hometown Address Proof</li>"
                + "<li>Educational Qualification Documents</li>"
                + "<li>Professional Qualification Documents</li>"
                + "<li>Medical Fitness Certificate</li>"
                + "<li>Experience / Relieving / Salary Statements</li>"

                + "</ol>"

                + "<div style='background:#fff7ed;"
                + "padding:14px;"
                + "border-radius:10px;"
                + "margin-top:20px;"
                + "color:#9a3412;'>"

                + "<b>Note:</b> "
                + "This secure upload link is valid for 48 hours."

                + "</div>"

                + "<br><br>"

                + "<p>"
                + "Warm Regards,<br>"
                + "<b>HR Department</b><br>"
                + "THE-CONNECTIONS ITES BUSINESS SERVICES PRIVATE LIMITED"
                + "</p>"

                + "</div>"

                + "</div>"

                + "</div>";
    }
}
