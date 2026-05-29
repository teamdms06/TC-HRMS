/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller.employeeMaster;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeSalary;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeSalaryRepository;
import com.tcInterviewform.TCInterviewForm.service.employeeMaster.EmployeeMasterService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author teamd
 */
@RestController
public class EmployeeMasterController {
    @Autowired
    EmployeeMasterService empMasterService;
    @Autowired
    HrmsEmployeeMasterRepository employeeMasterRepository;
     @Autowired
    HrmsEmployeeSalaryRepository salaryRepo;
    
  @GetMapping("/search-employees")
@ResponseBody
public List<Map<String, Object>> searchEmployees(
        @RequestParam String keyword
) {

    List<HrmsEmployeeMaster> employees =
            employeeMasterRepository
                    .findTop20ByEmployeeNameContainingIgnoreCaseOrEmployeeIdContainingIgnoreCase(
                            keyword,
                            keyword
                    );

    List<Map<String, Object>> list = new ArrayList<>();

    for (HrmsEmployeeMaster emp : employees) {

        Map<String, Object> map = new HashMap<>();

        map.put("id", emp.getId());

        map.put(
                "text",
                emp.getEmployeeName()
                + " - "
                + emp.getEmployeeId()
               );

        list.add(map);
    }

    return list;
}
    @GetMapping("/employee-master-details")
@ResponseBody
public Map<String, Object> getEmployeeDetails(@RequestParam Long employeeId) {

    HrmsEmployeeMaster emp = employeeMasterRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
//
    HrmsEmployeeSalary salary = salaryRepo
            .findTopByEmployeeMasterIdAndIsCurrentOrderByEffectiveFromDesc(employeeId, 1)
            .orElse(null);

    Map<String, Object> res = new HashMap<>();

    res.put("employeeName", emp.getEmployeeName());
    res.put("employeeId", emp.getEmployeeId());
    res.put("designation", emp.getDesignation());
    res.put("department", emp.getDepartment());
    res.put("grade", emp.getGrade());
    res.put("emailId", emp.getEmailId());
    res.put("contactNumber", emp.getContactNumber());
    res.put("gender", emp.getGender());
    res.put("dateOfJoining", emp.getDateOfJoining());
    res.put("dateOfBirth", emp.getDateOfBirth());
    res.put("location", emp.getLocation());
    res.put("aadhar", emp.getAadhar());
    res.put("pan", emp.getPan());
    res.put("uan", emp.getUan());
    res.put("pfStatus", emp.getPfStatus());
    res.put("education", emp.getEducation());
    res.put("address", emp.getEmployeeAddress());
    res.put("finalSalary", emp.getFinalSalary());
    res.put("state", emp.getState());
    res.put("city", emp.getCity());
    res.put("pincode", emp.getPincode());
    res.put("employeeAddress", emp.getEmployeeAddress());
    res.put("currentStatus", emp.getCurrentStatus());
//        System.out.println("salary--"+emp.getAadhar());
    if (salary != null) {
//        res.put("salary", salary);
        res.put("ctc", salary.getCtc());
        res.put("grossSalary", salary.getGrossSalary());
        res.put("netSalary", salary.getNetSalary());
        res.put("basic", salary.getBasic());
        res.put("hra", salary.getHra());
        res.put("employeePf", salary.getEmployeePf());
        res.put("professionalTax", salary.getProfessionalTax());
        res.put("conveyance", salary.getConveyance());
        res.put("specialAllowance", salary.getSpecialAllowance());
        res.put("insurance", salary.getInsuranceDeduction());
    }

    return res;
}


@PostMapping("/update-employee-basic-details")
@ResponseBody
public ResponseEntity<?> updateEmployeeBasicDetails(
        HttpServletRequest request,
        @RequestParam Long employeeId,
        @RequestParam String employeeName,
        @RequestParam String emailId,
        @RequestParam String contactNumber,
        @RequestParam(required = false) String alternateNumber,
        @RequestParam(required = false) String gender,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String department,
        @RequestParam(required = false) String designation,
        @RequestParam(required = false) String grade,
        @RequestParam(required = false) String education,
        @RequestParam(required = false) String employeeAddress,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String city,
        @RequestParam(required = false) String pincode,
        @RequestParam(required = false) String adhar,
        @RequestParam(required = false) String pan) {

    try {
        String updatedBy = (String) request.getSession().getAttribute("actorName");

        empMasterService.updateBasicDetails(
                employeeId, employeeName, emailId, contactNumber,
                alternateNumber, gender, location, department,
                designation, grade, education, employeeAddress,
                state, city, pincode, updatedBy,adhar,pan
        );

        return ResponseEntity.ok("Employee basic details updated successfully");

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
}
