/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.EmpFamilyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SIF
 */
@Repository
public interface EmpFamilyDetailsRepository extends JpaRepository<EmpFamilyDetails, Integer>{
    
}
