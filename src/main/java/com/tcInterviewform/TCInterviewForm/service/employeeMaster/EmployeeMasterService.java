/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.employeeMaster;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import com.tcInterviewform.TCInterviewForm.repository.employeeMaster.HrmsEmployeeMasterRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author teamd
 */
@Service
public class EmployeeMasterService {
     @Autowired
    HrmsEmployeeMasterRepository employeeMasterRepository;
    @Transactional(rollbackFor = Exception.class)
public void updateBasicDetails(
        Long employeeId,
        String employeeName,
        String emailId,
        String contactNumber,
        String alternateNumber,
        String gender,
        String location,
        String department,
        String designation,
        String grade,
        String education,
        String employeeAddress,
        String state,
        String city,
        String pincode,
        String updatedBy,
        String adhar,
        String pan) {

    HrmsEmployeeMaster emp = employeeMasterRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    
    employeeMasterRepository.backupEmployeeMasterBulk(
        Collections.singletonList(emp.getId()),
        "SINGLE_UPDATE_" + updatedBy
);

    emp.setEmployeeName(employeeName);
    emp.setEmailId(emailId);
    emp.setContactNumber(contactNumber);
    emp.setAlternateNumber(alternateNumber);
    emp.setGender(gender);
    emp.setLocation(location);
    emp.setDepartment(department);
    emp.setDesignation(designation);
    emp.setGrade(grade);
    emp.setEducation(education);
    emp.setEmployeeAddress(employeeAddress);
    emp.setState(state);
    emp.setCity(city);
    emp.setPincode(pincode);
    emp.setAadhar(adhar);
    emp.setPan(pan);

    employeeMasterRepository.save(emp);
}
    
}
