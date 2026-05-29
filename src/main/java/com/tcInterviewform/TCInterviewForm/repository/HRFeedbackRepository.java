/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.HrFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author SIF
 */
public interface HRFeedbackRepository extends JpaRepository<HrFeedback, Integer>{

    @Query(value = "SELECT * FROM `hr_feedback` WHERE applicant_id=?1",nativeQuery = true)
    public List<HrFeedback> getHrFeedbackbyJobAppId(Integer jAppId);

    
    
}
