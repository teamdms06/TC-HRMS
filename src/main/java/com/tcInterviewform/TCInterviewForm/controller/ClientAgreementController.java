///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.controller;
//
//import com.tcInterviewform.TCInterviewForm.model.ClientAgreement;
//import com.tcInterviewform.TCInterviewForm.model.DocumentMaster;
//import com.tcInterviewform.TCInterviewForm.model.HrFeedback;
//import com.tcInterviewform.TCInterviewForm.repository.ClientAgreementRepository;
//import com.tcInterviewform.TCInterviewForm.repository.DocumentMasterRepository;
//import java.io.IOException;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// *
// * @author teamd
// */
//@Controller
//public class ClientAgreementController {
//    
//    @Autowired
//    ClientAgreementRepository clientAgreementRepo;
//    @Autowired
//    DocumentMasterRepository docRepo;
//
//    //save feedback form
//    @PostMapping("saveClientAgreementForm")
//    public ModelAndView saveClientAgreementForm(HttpServletRequest request, HttpServletResponse response,
//            @ModelAttribute ClientAgreement clientAgreement, @RequestParam MultipartFile file
//    ) throws IOException {
//        HttpSession session = request.getSession();
//        String fileName = file.getOriginalFilename();
//        DocumentMaster doc = new DocumentMaster(fileName, file.getBytes());
//        DocumentMaster docMaster = docRepo.save(doc);
//        clientAgreement.setAgreementCopyId(docMaster.getId());
//        clientAgreement.setLastModifiedBy((String) session.getAttribute("actorName"));
//        clientAgreement.setStatus("ACTIVE");
//        clientAgreementRepo.save(clientAgreement);
//        LOGGER.info("saved");
//        //update status in applicaNT FORM   
//
//        return new ModelAndView("redirect:dashboard");
//    }
//    
//    @GetMapping("getAgreementDetails")
//    public ModelAndView getApplicantDetails(HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("id") Integer id, ModelMap model) {
//        // System.out.println("id>>" + jAppId);
//        List agreement = clientAgreementRepo.getAllAgreement(id);
////        List<HrFeedback> hrfeedback = hrmsService.getHRFeedbacks(jAppId);
//        //  System.out.println("---" + hrfeedback.get(0).getApplicantId());
//        model.addAttribute("agreement", agreement);
////        model.addAttribute("hrfeedbacks", hrfeedback);
//        model.addAttribute("jAppId", id);
//        // System.out.println("--"+newapplications.size()+"\n"+newapplications.get(0));
//        model.addAttribute("documentId", agreement.get(0));
//        return new ModelAndView("segments/ViewAgreementDetails", model);
//    }
//    
//}
