/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller;

import com.tcInterviewform.TCInterviewForm.communication.SendMessage.SendMessage;
import com.tcInterviewform.TCInterviewForm.communication.SendMessage.SendSMS;
import com.tcInterviewform.TCInterviewForm.model.City;
import com.tcInterviewform.TCInterviewForm.model.Otp;
import com.tcInterviewform.TCInterviewForm.model.State;
import com.tcInterviewform.TCInterviewForm.repository.CityRepository;
import com.tcInterviewform.TCInterviewForm.repository.OTPRepository;
import com.tcInterviewform.TCInterviewForm.repository.StateRepository;
import com.tcInterviewform.TCInterviewForm.service.HRMSService;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SIF
 */
@Controller
public class MainController {

    @Autowired
    StateRepository stateRepo;
    @Autowired
    CityRepository cityRepo;
    @Autowired
    OTPRepository otpRepo;
    @Autowired
    HRMSService hrmsService;
    @Autowired
    private SendMessage sm;
    @Autowired
    SendSMS sendSMS;
//    @Autowired
//    ClientAgreementRepository clientRepo;

    @RequestMapping("/")
    public String firstPage(Model model) {
        List<State> sList = stateRepo.findAll();
        model.addAttribute("slist", sList);
        //  model.addAttribute("clist", cList);
        return "jobapplicants";
//        return "temp";

    }

    @RequestMapping(value = "getOtp")
    @ResponseBody
    public String getOtp(@RequestParam("phoneNo") String phoneNo, HttpServletRequest request) throws UnsupportedEncodingException {
        try {

            HttpSession session = request.getSession();
            Otp otpPojo = new Otp();
            Random random = new Random();
            Date date = new Date();
            String otp = String.valueOf(random.nextInt(10000));
//        String otp = "1234";
            //send otp via sms 04-11-2022
            String messageTemplate = "From The-Connections, Your verification code is : " + otp;
//       old sms sigma cmted by nitin 07122022
//        sm.sendText(phoneNo, messageTemplate);
            sendSMS.SendSMS(messageTemplate, phoneNo);
            otpPojo.setOtpPhone(phoneNo);
            otpPojo.setOtpText(otp);
            otpPojo.setOtpCreatedAt(date);
            otpPojo.setOtpUpdatedAt(date);
            otpRepo.save(otpPojo);
            session.setAttribute("otpId", otpPojo.getOtpId());
            List list = new ArrayList();
            list.add(otp);
            return otp;
        } catch (Exception e) {
            return "";
        }
    }

    @RequestMapping(value = "login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("dashboard")
    public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        
        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login", model);
        } else {
            String role = (String) session.getAttribute("actor_process");
            Integer userId = (Integer) session.getAttribute("IDs");
//            System.out.println("role"+role);
            if (role.equalsIgnoreCase("HR")) {
                hrmsService.getHRDashboardCounts(userId, model);
            } else if (role.equalsIgnoreCase("TL")) {
                hrmsService.getTLDashboardCounts(userId, model);
            } else if (role.equalsIgnoreCase("Admin")) {
                hrmsService.getAdminDashboardCounts(userId, model);
            } else {
//                System.out.println("ree");
                hrmsService.getAdminDashboardCounts(userId, model);
            }
            //chart data
            ArrayList<String> getlast3Months = new ArrayList();
            List<Object[]> getAllLast3Months = hrmsService.getAllLast3Months();
            List<Object[]> getSelectedLast3Months = hrmsService.getSelectedLast3Months();
            List<Object[]> getRejectdLast3Months = hrmsService.getRejectedLast3Months();
            SimpleDateFormat format = new SimpleDateFormat("MMMM");
            Calendar c = Calendar.getInstance();
            Calendar c1 = Calendar.getInstance();
            c.setTime(new Date());
            c1.setTime(new Date());
            c.add(Calendar.MONTH, -2);
            c1.add(Calendar.MONTH, -1);
            Date d = c.getTime();
            Date d1 = c1.getTime();
            String res = format.format(d);
            String res1 = format.format(d1);
            getlast3Months.add(res);
            getlast3Months.add(res1);
            getlast3Months.add(format.format(new Date()));
            int[] allAppData = new int[]{0, 0, 0};
            int[] selectedData = new int[]{0, 0, 0};
            int[] rejectedData = new int[]{0, 0, 0};
            for (Object[] result : getAllLast3Months) {
                String month = (String) result[0];
                Integer count = ((BigInteger) result[1]).intValue();
                for (int i = 0; i <= getlast3Months.size() - 1; i++) {
                    if (getlast3Months.get(i).equalsIgnoreCase(month)) {
                        allAppData[i] = count;
                    }
                }
            }
            for (Object[] result : getSelectedLast3Months) {
                String month = (String) result[0];
                Integer count = ((BigInteger) result[1]).intValue();
                for (int i = 0; i <= getlast3Months.size() - 1; i++) {
                    if (getlast3Months.get(i).equalsIgnoreCase(month)) {
                        selectedData[i] = count;
                    }
                }
            }
            for (Object[] result : getRejectdLast3Months) {
                String month = (String) result[0];
                Integer count = ((BigInteger) result[1]).intValue();
                for (int i = 0; i <= getlast3Months.size() - 1; i++) {
                    if (getlast3Months.get(i).equalsIgnoreCase(month)) {
                        rejectedData[i] = count;
                    }
                }
            }
            model.addAttribute("last3Months", getlast3Months);
            model.addAttribute("getAllLast3Months", Arrays.toString(allAppData));
            model.addAttribute("getSelectedLast3Months", Arrays.toString(selectedData));
            model.addAttribute("getRejectdLast3Months", Arrays.toString(rejectedData));
//             model.addAttribute("overAllperData", Arrays.toString(overAllperData));
            System.out.println("Rooole" + role);
            if (role.equalsIgnoreCase("Trainer")) {
                return new ModelAndView("segments/trainerDashboard", model);
            } else {
                return new ModelAndView("segments/dashboard", model);
            }
            // return new ModelAndView("segments/dashboard", model);
            // }
        }
    }

    @GetMapping("freshData")
    public ModelAndView freshData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login", model);
        } else {
            String role = (String) session.getAttribute("actor_process");
            Integer HrId = (Integer) session.getAttribute("IDs");
//            String role = "HR";
            String status;
            List newapplications;
//            if (role.equalsIgnoreCase("HR")) {
//                status = "FRESH";
//                newapplications = hrmsService.getNewApplications(status, role,HrId);
//            }else 
            if (role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("HR")) {
                status = "FRESH";
                newapplications = hrmsService.getNewApplications(status, role, HrId);
            } else {
                status = "SHORTLISTED";
                newapplications = hrmsService.getStatusWiseApplications(status);
            }
//             newapplications = hrmsService.getNewApplications(status,HrId);
            model.addAttribute("itrdata", newapplications);
            model.addAttribute("status", status);

            return new ModelAndView("segments/freshData", model);

        }

    }

    @GetMapping("clientRound")
    public ModelAndView clientRound(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login", model);
        } else {
            String role = (String) session.getAttribute("actor_process");
            Integer HrId = (Integer) session.getAttribute("IDs");
//            String role = "HR";
            String status;
            List newapplications;
            if (role.equalsIgnoreCase("HR")) {
                status = "clientRound";
                newapplications = hrmsService.getNewApplications(status, role, HrId);
                model.addAttribute("itrdata", newapplications);
                model.addAttribute("status", status);
            }
//             newapplications = hrmsService.getNewApplications(status,HrId);

            return new ModelAndView("segments/freshData", model);

        }

    }

    @GetMapping("nxtRound")
    public ModelAndView nxtRound(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login", model);
        } else {
            String role = (String) session.getAttribute("actor_process");
            Integer HrId = (Integer) session.getAttribute("IDs");
//            String role = "HR";

            String status = "NxtRound";
            List newapplications = hrmsService.getNewApplications(status, role, HrId);

//             newapplications = hrmsService.getNewApplications(status,HrId);
            model.addAttribute("itrdata", newapplications);
            model.addAttribute("status", status);

            return new ModelAndView("segments/freshData", model);

        }

    }

    @GetMapping("hold")
    public ModelAndView hold(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        String status = "HOLD";
        if (session.isNew()) {
            return new ModelAndView("Login", model);
        } else {
            List newapplications = hrmsService.getStatusWiseApplications(status);
            model.addAttribute("itrdata", newapplications);
            model.addAttribute("status", status);
            return new ModelAndView("segments/hold", model);

        }

    }

    @GetMapping("selected")
    public ModelAndView selected(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("Login", model);
        } else {

            String role = "HR";
            if (role.equalsIgnoreCase("HR")) {
                String status = "SELECTED";
                List newapplications = hrmsService.getStatusWiseApplications(status);
                model.addAttribute("itrdata", newapplications);
                model.addAttribute("status", status);
            }
            return new ModelAndView("segments/selected", model);

        }

    }

    @GetMapping("rejected")
    public ModelAndView rejected(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("Login", model);
        } else {

            String role = "HR";
            if (role.equalsIgnoreCase("HR")) {
                String status = "REJECTED";
                List newapplications = hrmsService.getStatusWiseApplications(status);
                model.addAttribute("itrdata", newapplications);
                model.addAttribute("status", status);
            }

            return new ModelAndView("segments/rejected", model);

        }

    }

    @GetMapping("hrAttempt")
    public ModelAndView hrAttempted(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();

        if (session.isNew()) {
            return new ModelAndView("Login", model);
        } else {
            String role = (String) session.getAttribute("actor_process");
//            String role = "HR";
            if (!role.equalsIgnoreCase("HR")) {
                String status = "REJECTED";
                List newapplications = hrmsService.getStatusWiseApplications(status);
                model.addAttribute("itrdata", newapplications);
                model.addAttribute("status", status);
            }

            return new ModelAndView("segments/rejected", model);

        }

    }

    //page redirect
    @RequestMapping(value = "defaultSuccess")
    public String successHandler() {
        return "defaultSuccess";
    }

    @RequestMapping(value = "errorPage")
    public String errorHandler() {
        return "errorPage";
    }

    //get City list
    @RequestMapping(value = "/getCityList", method = RequestMethod.GET)
    public String getCityList(@RequestParam("stateId") Integer stateId, Model model) {
        List<City> ctList = cityRepo.getCityListById(stateId);
        model.addAttribute("ctList", ctList);
        return "segments/fragments/common::CityList";
    }

    @GetMapping("metaAddsLeadData")
    public ModelAndView metaAddsLeadData(@RequestParam(required = false) String status, HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("login", model);
        } else {
//            String status="NEW";
            List metaAddsLead = hrmsService.getNewMetaAddsLead(status);
            model.addAttribute("itrdata", metaAddsLead);
            model.addAttribute("status", status);

            return new ModelAndView("segments/meatAddsLead", model);

        }

    }

//    @GetMapping("letterIssueOld")
//    public ModelAndView letterIssueOld(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
//
//        HttpSession session = request.getSession();
////        System.out.println(""+session.isNew());
//        if (session == null || session.getAttribute("actorName") == null) {
////                System.out.println("");
//            return new ModelAndView("login", model);
//        }
//        if (session.isNew()) {
//            return new ModelAndView("login", model);
//        } else {
////            System.out.println("zxcvbn");
//            String status = "NEW";
//            List letterIssue = hrmsService.getletterIssueOld(status);
//            model.addAttribute("itrdata", letterIssue);
//            model.addAttribute("status", status);
//
//            return new ModelAndView("segments/letterIssue/oldEmployeeDetails", model);
//
//        }
//
//    }
    @GetMapping("documentCenter")
    public ModelAndView documentCenter(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("Login", model);
        }
        String role = (String) session.getAttribute("actor_process");
//        System.out.println("role+"+role);
        if (role == null || role.equalsIgnoreCase("TL")) {

            return new ModelAndView("Login", model);

        }
        return new ModelAndView("segments/documentCenter", model);

    }

    @GetMapping("employee-master")
    public ModelAndView employeeMaster(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        HttpSession session = request.getSession();
        if (session.isNew()) {
            return new ModelAndView("Login", model);
        }
        String role = (String) session.getAttribute("actor_process");
        if (role == null || role.equalsIgnoreCase("HR")||role.equalsIgnoreCase("TL")) {

            // Optional: Access denied page
            model.addAttribute("msg", "Access Denied");

            return new ModelAndView("Login", model);

        }

        return new ModelAndView("segments/employeeMasterPage", model);

    }
}
