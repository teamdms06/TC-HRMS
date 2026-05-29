/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.MetaAddsFollowup;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface MetaAddsLeadFollowupRepository extends JpaRepository<MetaAddsFollowup, Integer>{

    @Query(value = "SELECT * FROM `meta_adds_followup` WHERE lead_id=?1",nativeQuery = true)
    public List<MetaAddsFollowup> findByLeadId(Integer leadId);
    
}
