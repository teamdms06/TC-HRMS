/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.EmpSkills;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author SIF
 */
public interface EmpSkillRepository  extends JpaRepository<EmpSkills, Integer>{
    
}
