///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.controller;
//
//import com.tcInterviewform.TCInterviewForm.model.TrainingBatches;
//import com.tcInterviewform.TCInterviewForm.repository.TrainingBatchRepository;
//import com.tcInterviewform.TCInterviewForm.service.HRMSService;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// *
// * @author SIF
// */
//@Controller
//public class TrainingBatchController {
//
//    @Autowired
//    TrainingBatchRepository trainingBatchRepo;
//    @Autowired
//    HRMSService hrmsService;
//
//    //save batch
//    @PostMapping("addNewBatch")
//    public ModelAndView addNewBatchForm(HttpServletRequest request, HttpServletResponse response,
//            @ModelAttribute TrainingBatches trainingBatch) {
//        System.out.println("data>>>" + trainingBatch);
//        trainingBatch.setBatchStatus("NEW");
//        trainingBatchRepo.save(trainingBatch);
//        //email to trainer
//        return new ModelAndView("redirect:trainingBatches");
//    }
//
//    @GetMapping("trainingBatches")
//    public ModelAndView trainingBatches(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//
//        HttpSession session = request.getSession();
//        if (session.isNew()) {
//            return new ModelAndView("Login", model);
//        } else {
//
//            String role = "HR";
//            if (role.equalsIgnoreCase("HR")) {
//                //  String status = "REJECTED";
//                List batchList = hrmsService.getBatchList();
//
//                model.addAttribute("batchList", batchList);
//                // model.addAttribute("status", status);
//            }
//        }
//        return new ModelAndView("segments/trainings", model);
//
//    }
//
//    @RequestMapping(value = "getBatchDetailsById", method = RequestMethod.POST)
//    public String getTicketDetails(
//            @RequestParam("BatchId") Integer batchId,
//            Model model) {
//
//        List<TrainingBatches> list = trainingBatchRepo.findByBatchId(batchId);
//
//        //get trainer Deatails
//        String trainer = hrmsService.getTrainerNameById(list.get(0).getTrainerName());
//        List<String> myList = new ArrayList<String>(Arrays.asList(list.get(0).getAgentList().split(",")));
//        List agentList = hrmsService.getAgentNameList(myList);
//        System.out.println("agentList--" + agentList);
//        model.addAttribute("trainer", trainer);
//        model.addAttribute("batchData", list);
//        model.addAttribute("agentList", agentList);
//
//        return "segments/fragments/viewBatchList::viewBatchListData";
//    }
//
//    @RequestMapping(value = "updateBatchDetailsById", method = RequestMethod.GET)
//    public String updateBatchDetailsById(
//            @RequestParam("BatchId") Integer batchId,
//            Model model) {
//
//        List<TrainingBatches> list = trainingBatchRepo.findByBatchId(batchId);
//        model.addAttribute("trainer", list);
//        return "segments/fragments/BatchForm::batchformfrag";
//    }
//
//    @PostMapping("updateBatchDeatils")
//    public ModelAndView updateBatchDeatils(HttpServletRequest request, HttpServletResponse response,
//            @ModelAttribute TrainingBatches trainingBatch) {
//        System.out.println("data>>>" + trainingBatch);
//        // trainingBatch.setBatchStatus("NEW");
//        TrainingBatches tb = trainingBatchRepo.findByBId(trainingBatch.getId());
//        String agLIst = tb.getAgentList();
//        if (trainingBatch.getAgentList()!= null) {
//            String upAgentList = agLIst.concat("," + trainingBatch.getAgentList());
//            trainingBatch.setAgentList(upAgentList);
//        }else{
//             trainingBatch.setAgentList(agLIst);
//        }
//        String upRemark = tb.getRemark().concat("\n" + trainingBatch.getRemark());
//       
//        trainingBatch.setRemark(upRemark);
////         System.out.println("--"+upRemark);
//        trainingBatchRepo.save(trainingBatch);
//        //email to trainer
//        return new ModelAndView("redirect:trainingBatches");
//    }
//
//}
