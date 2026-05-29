/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository.LetterIssue;

import com.tcInterviewform.TCInterviewForm.model.LetterIssue.IssueletterMaster;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface IssueLetterMasterRepository extends JpaRepository<IssueletterMaster, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM issueletter_master WHERE form_id = ?1 order by id desc limit 1", nativeQuery = true)
    public void deleteByFormId(Integer letterId);

}
