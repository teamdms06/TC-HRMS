/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.JobApplicants;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SIF
 */
@Repository
public interface JobApplicantRepository extends JpaRepository<JobApplicants, Integer> {

    @Query(value = "SELECT feedback_count FROM `job_applicants` WHERE jobapp_id=?1", nativeQuery = true)
    public int getFeedCount(Integer applicantId);

    @Query(value = "SELECT * FROM `job_applicants` WHERE jobapp_id=?1", nativeQuery = true)
    public JobApplicants findByappId(Integer applicantId);

    @Query(value = "SELECT `jobapp_id`,`jobapp_updated_at`,`jobapp_interviewer_name`,`jobapp_phone`,`jobapp_email`,`jobapp_consultancy_walkin_name`,`offer_letter_status`,`loi_status`FROM`job_applicants` ORDER BY `jobapp_updated_at` DESC", nativeQuery = true)
    public List getletterIssueDataList();

    @Query(value = "SELECT \n"
            + "    ja.jobapp_id,\n"
            + "    ja.jobapp_updated_at,\n"
            + "    ja.jobapp_interviewer_name,\n"
            + "    ja.jobapp_phone,\n"
            + "    ja.jobapp_email,\n"
            + "    ja.jobapp_consultancy_walkin_name,\n"
            + "\n"
            + "    hrf.`created_at`,\n"
            + "    hrf.`interviewer_name`,\n"
            + "    hrf.`offered_salary`,\n"
            + "    ja.`jobapp_time_required_for_joining_if_select`,\n"
            + "\n"
            + "    ja.`offer_letter_status`,\n"
            + "    ja.`loi_status`,\n"
            + "    ja.`jobapp_address_for_correspondance1`,\n"
            + "    ja.`jobapp_city`,\n"
            + "    ja.`jobapp_state`,\n"
            + "    ja.`jobapp_postal_code`,\n"
            + "    ja.`jobapp_inteview_venue`,\n"
            + "    ja.`work_location`,\n"
            + "    ja.`department`,\n"
            + "    ja.`jobapp_gender`,\n"
            + "    ja.`jobapp_birthdate`\n"
            + "\n"
            + "FROM job_applicants ja\n"
            + "\n"
            + "INNER JOIN hr_feedback hrf \n"
            + "    ON hrf.applicant_id = ja.jobapp_id\n"
            + "\n"
            + "WHERE \n"
            + "    ja.status = 'SELECTED'\n"
            + "    AND ja.jobapp_id = ?1 \n"
            + "    AND hrf.`final_status` = 'selected'\n"
            + "    AND hrf.id = (\n"
            + "        SELECT id\n"
            + "        FROM hr_feedback\n"
            + "        WHERE applicant_id = ja.jobapp_id\n"
            + "          AND `final_status` = 'selected'\n"
            + "        ORDER BY id DESC\n"
            + "        LIMIT 1\n"
            + "    )", nativeQuery = true)
    public List getUniqueletterIssueDetails(Integer lId);

    @Modifying
    @Transactional
    @Query(value = "update job_applicants set salary_bifurcation=?1 where jobapp_id = ?2", nativeQuery = true)
    public void updateSalBifircationId(Long id, Integer applicantId);

    @Modifying
    @Transactional
    @Query(value = "update job_applicants set offer_letter_status=?1 where jobapp_id = ?2", nativeQuery = true)
    public void updateOfferStatus(String status, Integer applicantId);

    @Modifying
    @Transactional
    @Query(value = "update job_applicants set loi_status=?1 where jobapp_id = ?2", nativeQuery = true)
    public void updateLOIStatus(String status, Integer letterId);

}
