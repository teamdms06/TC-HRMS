/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller.LetterIssue;

import com.tcInterviewform.TCInterviewForm.model.LetterIssue.EmployeeSalary;
import com.tcInterviewform.TCInterviewForm.repository.JobApplicantRepository;
import com.tcInterviewform.TCInterviewForm.repository.LetterIssue.EmployeeSalaryRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author teamd
 */
@Controller
public class SalaryController {

    @Autowired
    private EmployeeSalaryRepository repository;
    @Autowired
    private JobApplicantRepository jApptRepo;

    @PostMapping("/saveSalary")
    @ResponseBody
    public String saveSalary(HttpServletRequest request, HttpServletResponse response,@ModelAttribute EmployeeSalary salary) {
//        System.out.println("erew" + salary.toString());
        HttpSession session = request.getSession();
        String userName=(String)  session.getAttribute("actorName");
        salary.setUpdatedBy(userName);
        EmployeeSalary sal = repository.save(salary);
        //add in applicant form
        jApptRepo.updateSalBifircationId(sal.getId(), sal.getApplicantId());
        return "success";
    }

    @GetMapping("/getSalaryById")
    @ResponseBody
    public EmployeeSalary getSalary(@RequestParam Integer jobappId) {
//        System.out.println("dsfdgfb" + jobappId);
        return repository.findTopByJobappIdOrderByIdDesc(jobappId);
    }
}
