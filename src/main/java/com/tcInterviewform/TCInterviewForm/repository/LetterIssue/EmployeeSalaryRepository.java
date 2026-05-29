/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.LetterIssue;

import com.tcInterviewform.TCInterviewForm.model.LetterIssue.EmployeeSalary;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface EmployeeSalaryRepository extends JpaRepository<EmployeeSalary, BigInteger>{

    @Query(value = "select * from employee_salary where applicant_id=?1 order by id desc limit 1",nativeQuery = true)
    public EmployeeSalary findTopByJobappIdOrderByIdDesc(Integer jobappId);

   
    
}
