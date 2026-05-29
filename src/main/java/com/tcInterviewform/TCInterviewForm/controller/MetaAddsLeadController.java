/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller;

import com.tcInterviewform.TCInterviewForm.model.MetaAddsFollowup;
import com.tcInterviewform.TCInterviewForm.service.MetaAddsLeadService;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author teamd
 */
@Controller
public class MetaAddsLeadController {

    @Autowired
    MetaAddsLeadService metaAddsService;

    @GetMapping("/syncData")
    @ResponseBody
    public String manualSync() {
        System.out.println("MAnual sync");
        try {
            metaAddsService.syncData(); // ✅ transaction works
            return "success";
        } catch (Exception e) {

            System.out.println("Auto Sync Failed: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("getMetaAddsDeatails")
    public ModelAndView getMetaAddsDeatails(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("id") Integer leadId, @RequestParam("status") String status, ModelMap model) {
        // System.out.println("id>>" + jAppId);
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("actorName") == null) {
//                System.out.println("");
            return new ModelAndView("login", model);
        } else {
            List leadDeatils = metaAddsService.getmetaAddsLEadsDetails(leadId);
            List<MetaAddsFollowup> hrfeedback = Collections.emptyList();
            if (!status.equalsIgnoreCase("New")) {
                hrfeedback = metaAddsService.getAddsLeadFeedbacks(leadId);
            }
            model.addAttribute("getAllDetails", leadDeatils);
            model.addAttribute("hrfeedbacks", hrfeedback);
            model.addAttribute("leadId", leadId);
            Integer nextId = metaAddsService.findNextLeadId(leadId);

            model.addAttribute("nextLeadId", nextId);
            return new ModelAndView("segments/meatAddsLeadDetails", model);
        }
    }

    @PostMapping("saveAddsLeadFeedback")
    @ResponseBody
    public String saveAddsLeadFeedbackForm(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute MetaAddsFollowup hrFeedback, final Locale locale, ModelMap model) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return "login";
        } else {
            String actorName = (String) session.getAttribute("actorName");
            hrFeedback.setActionBy(actorName);
            MetaAddsFollowup followup = metaAddsService.saveAddsFollowup(hrFeedback);
            //update Status
            int status = metaAddsService.updateCallStatus(hrFeedback.getLeadId(), followup.getStatus());
            if (followup.getStatus().equalsIgnoreCase("Interested")) {
                //send interview form link 
                //get Aplicant mail id
                String applicantMailId = metaAddsService.getApplicantMailId(followup.getLeadId());
                if (applicantMailId != null && !applicantMailId.trim().isEmpty()) {
//                    Boolean mailstatus = metaAddsService.mailSender(applicantMailId, locale);
                    Boolean mailstatus = true;
                }
            }
            if (status == 1) {
                return "Suceess -" + hrFeedback.getLeadId();
            } else {
                return "Failed -" + hrFeedback.getLeadId();
            }
        }
    }
}
