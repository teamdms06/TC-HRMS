/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller;

import com.tcInterviewform.TCInterviewForm.communication.service.EmailSender;
import com.tcInterviewform.TCInterviewForm.model.HrFeedback;
import com.tcInterviewform.TCInterviewForm.model.JobApplicants;
import com.tcInterviewform.TCInterviewForm.model.ResumesMaster;
import com.tcInterviewform.TCInterviewForm.repository.EmpFamilyDetailsRepository;
import com.tcInterviewform.TCInterviewForm.repository.EmpPreviousExpericeRepository;
import com.tcInterviewform.TCInterviewForm.repository.EmpSkillRepository;
import com.tcInterviewform.TCInterviewForm.repository.HRFeedbackRepository;
import com.tcInterviewform.TCInterviewForm.repository.JobApplicantRepository;
import com.tcInterviewform.TCInterviewForm.repository.ResumeMasterRepository;
import com.tcInterviewform.TCInterviewForm.service.HRMSService;
import com.tcInterviewform.TCInterviewForm.service.S3Service;
import com.tcInterviewform.TCInterviewForm.util.FileHelper;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SIF
 */
@Controller
public class ApplicationDetailsSaveController {

    @Autowired
    JobApplicantRepository applicantRepo;
    @Autowired
    EmpSkillRepository skillRepo;

    @Autowired
    EmpPreviousExpericeRepository experianceRepo;

    @Autowired
    EmpFamilyDetailsRepository familyDtlRepo;
    @Autowired
    EmailSender emailSender;
    @Autowired
    HRMSService hrmsService;
    @Autowired
    FileHelper fileHelper;
    @Autowired
    HRFeedbackRepository hrFeedbackRepo;
    @Autowired
    ResumeMasterRepository resumeMasterRepo;

    @Autowired
    S3Service s3Service;
    private static final Logger LOGGER = LogManager.getLogger(ApplicationDetailsSaveController.class);

    @PostMapping("/saveApplicantDetail")
    @ResponseBody
    public ResponseEntity<String> saveApplicantData(
            @RequestParam("list") String skillsJson,
            @RequestParam("list1") String experienceJson,
            @RequestParam("list3") String jobAppJson,
            @RequestParam("file") MultipartFile file,
            final Locale locale) {

        try {
            boolean mailStatus = hrmsService.saveCompleteApplicant(
                    skillsJson,
                    experienceJson,
                    jobAppJson,
                    file,
                    locale
            );

            if (mailStatus) {
                return ResponseEntity.ok("Done");
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Data saved but mail not sent");

        } catch (Exception e) {
            LOGGER.error("Error while saving applicant", e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong");
        }
    }

    @GetMapping("getapplicantDetails")
    public ModelAndView getApplicantDetails(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id") Integer jAppId, ModelMap model) {
//         System.out.println("id>>" + jAppId);
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login");
        } else {
            List newapplications = hrmsService.getAllNewApplications(jAppId);
            List<HrFeedback> hrfeedback = hrmsService.getHRFeedbacks(jAppId);
            model.addAttribute("getAllDetails", newapplications);
            model.addAttribute("hrfeedbacks", hrfeedback);
            model.addAttribute("jAppId", jAppId);
            Object[] row = (Object[]) newapplications.get(0);
            Object status = row[16];
            model.addAttribute("status", status);
            return new ModelAndView("segments/applicantDetails", model);
        }
    }

    //save feedback form
    @PostMapping("saveFeedback")
    public ModelAndView saveFeedbackForm(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute HrFeedback hrFeedback, final Locale locale
    ) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login");
        } else {
            int feedCount = applicantRepo.getFeedCount(hrFeedback.getApplicantId());
            String actorName = (String) session.getAttribute("actorName");
            hrFeedback.setInterviewerName(actorName);
            hrFeedback.setFeedbackAttempt(feedCount + 1);
            hrmsService.saveFeedback(hrFeedback);
            LOGGER.info("feedback has been saved");
            //update status in applicaNT FORM   
            Integer nxtRound = hrFeedback.getAssignTo();
            hrmsService.updateApplicationStatus(hrFeedback.getApplicantId(), hrFeedback.getFinalStatus(), hrFeedback.getFeedbackAttempt(), nxtRound, hrFeedback.getOfferedSalary());
            //send mail notification for nxt round
            JobApplicants jobapplier = hrmsService.getJobapplierByJobId(hrFeedback.getApplicantId());
            if (jobapplier.getStatus().equalsIgnoreCase("NxtRound")) {
                Boolean mailstatus = hrmsService.NextRoundmailSender(jobapplier, "nxtRound", locale);
            }
            return new ModelAndView("redirect:dashboard");
        }
    }

    @GetMapping("getIframeRemsume")
    @ResponseBody
    public Map<String, String> fileData(@RequestParam("jappId") int id, HttpSession session, Model model) throws UnsupportedEncodingException {

        ResumesMaster file = resumeMasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + id));
        Map<String, String> response = new HashMap<>();
        response.put("fileName", file.getFileName());
        String key =
        file.getCvUrl().substring(file.getCvUrl().lastIndexOf("/") + 1);
        String preSignedurl=s3Service.generatePresignedUrl(key);
        response.put("url", preSignedurl);
//        response.put("url", file.getCvUrl());
        return response;
//        return base64Encoded;
//        return file121;
    }

    @PostMapping("/saveJobApplication")
    @ResponseBody
    public String saveJobApplication(
            @ModelAttribute JobApplicants form,
            @RequestParam("resume") MultipartFile file) {

        System.out.println("Name: " + form.getJobappInterviewerName());
        System.out.println("File: " + file.getOriginalFilename());
        JobApplicants jobapplier = applicantRepo.save(form);
        return "Success";
    }

    @PostMapping("/updateApplicantDetails")
    @ResponseBody
    public String updateApplicant(JobApplicants applicant) {
//         System.out.println("appl"+applicant.toString());
        hrmsService.updateApplicant(applicant);

        return "success";
    }

}
