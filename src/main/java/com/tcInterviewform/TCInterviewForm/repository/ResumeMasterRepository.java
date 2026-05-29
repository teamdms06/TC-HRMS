/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.ResumesMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SIF
 */
@Repository
public interface ResumeMasterRepository extends JpaRepository<ResumesMaster, Integer>{
    
    @Query(value = "SELECT DATA FROM`resumes_master` WHERE id=?1",nativeQuery = true)
    public byte[] getFile(int id);
    
}
