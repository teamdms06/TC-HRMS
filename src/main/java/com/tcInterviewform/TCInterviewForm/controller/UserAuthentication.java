/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller;

import com.tcInterviewform.TCInterviewForm.model.User;
import com.tcInterviewform.TCInterviewForm.repository.USerRepository;
import com.tcInterviewform.TCInterviewForm.security.JwtTokenProvider;
import com.tcInterviewform.TCInterviewForm.security.PasswordService;
import com.tcInterviewform.TCInterviewForm.service.HRMSService;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author SIF
 */
@Controller
public class UserAuthentication {

    @Autowired
    USerRepository userRepository;
    @Autowired
    HRMSService hrmsService;
    @Autowired
    PasswordService passwordService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private User user = null;

    @RequestMapping(value = "loginFormData", method = RequestMethod.POST)
    public ModelAndView getAgentDashboard(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response,
            ModelMap model,
            HttpSession sessio1) {
        String msg = "";
//        System.out.println("USsername>>" + username);
        //Check if credentials are Correct
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception ex) {
            msg = "Invalid Username or Password";
            model.addAttribute("msg", msg);
            ex.printStackTrace();
        }
        //Here if IDs NULL means it did not find valid user name or password 
        if (!passwordService.verifyPassword(user, password)) {//Show Msg for wrong user.
            msg = "Invalid Username or Password";
            //   System.out.println("msg <<>>" + msg);
            model.addAttribute("msg", msg);
        } else {
            HttpSession session = request.getSession(true);
            //storing user info into session 
            session.setAttribute("IDs", user.getId());
            session.setAttribute("actorName", user.getName());
            session.setAttribute("actor_process", user.getDepartment());
            String department = user.getDepartment() == null ? "USER" : user.getDepartment().trim().toUpperCase();
            String roleName = "ROLE_" + department.replaceAll("[^A-Z0-9]", "_");
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, Arrays.asList(new SimpleGrantedAuthority(roleName)));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            String jwtToken = jwtTokenProvider.generateToken(authentication);
            session.setAttribute("JWT_TOKEN", jwtToken);
            System.out.println("JWT generated for user: " + jwtToken);
            String role = (String) session.getAttribute("actor_process");
            Integer userId = (Integer) session.getAttribute("IDs");
             if (role.equalsIgnoreCase("HR")) {
                hrmsService.getHRDashboardCounts(userId, model);
            } else if (role.equalsIgnoreCase("TL")) {
                hrmsService.getTLDashboardCounts(userId, model);
            } else if (role.equalsIgnoreCase("Admin")) {
                hrmsService.getAdminDashboardCounts(userId, model);
            }
            else {
                hrmsService.getAdminDashboardCounts(userId, model);
            }
//            if (role.equalsIgnoreCase("HR")) {
//                hrmsService.getHRDashboardCounts(userId,model);
//            } else if (role.equalsIgnoreCase("TL")) {
//                hrmsService.getTLDashboardCounts(userId,model);
//            } else {
//                //hrmsService.getSuperAdminDashboardCounts(model);
//            }
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
            return new ModelAndView("segments/dashboard", model);
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(value = "logout")
    public String getLogin(HttpServletRequest request) {
        //  System.out.println("Logout");
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "login";
    }
    
    
      //get trainer list by Nitin 11022023
      //get City list
//    @RequestMapping(value = "/getTrainerList", method = RequestMethod.GET)
//    public String getTrainerList(Model model) {
//        String department="Trainer";
//        List<User> trainerList = userRepository.getTrainerList(department);
//        model.addAttribute("trainerList", trainerList);
//        return "segments/fragments/common::TrainerList";
//    }
//    @RequestMapping(value = "/getAgentList", method = RequestMethod.GET)
//    public String getAgentList(Model model) {
//       
//        List selectedAgentList = hrmsService.getSelectedAgentList();
//        model.addAttribute("AgentList", selectedAgentList);
//        return "segments/fragments/common::SelectedAgentList";
//    }
    
    @RequestMapping(value = "/getTLList", method = RequestMethod.GET)
    public String getTLList( Model model) {
        List<User> ulist = userRepository.getTLList();
        model.addAttribute("ulist", ulist);
        return "segments/fragments/common::UserList";
    }
}
