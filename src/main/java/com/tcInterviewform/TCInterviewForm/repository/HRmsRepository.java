/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.JobApplicants;
import java.util.ArrayList;
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
public interface HRmsRepository extends JpaRepository<JobApplicants, Integer> {

    @Query(value = "SELECT jp.`jobapp_id`,`jobapp_interviewer_name`,`jobapp_phone`,`jobapp_birthdate`,`jobapp_created_at`, IF(epe.`exp_designation`=\"NA\",'Fresher','Experienced') AS experience,STATUS\n"
            + "FROM`job_applicants` AS jp INNER JOIN `emp_previous_experice` AS epe ON jp.`jobapp_id`=epe.`exp_jobapp_id`\n"
            + "WHERE STATUS=?1 and next_round=?2 GROUP BY exp_jobapp_id ORDER BY jobapp_created_at ASC", nativeQuery = true)
    public List getNewApplications(String status, Integer id);

    @Query(value = "SELECT jp.`jobapp_id`,`jobapp_interviewer_name`,`jobapp_phone`,`jobapp_birthdate`,`jobapp_created_at`, IF(epe.`exp_designation`=\"NA\",'Fresher','Experienced') AS experience,STATUS\n"
            + "FROM`job_applicants` AS jp INNER JOIN `emp_previous_experice` AS epe ON jp.`jobapp_id`=epe.`exp_jobapp_id`\n"
            + "WHERE STATUS=?1 GROUP BY exp_jobapp_id ORDER BY jobapp_created_at ASC", nativeQuery = true)
    public List getStatusWiseApplications(String status);

    @Query(value = "SELECT jp.`jobapp_id`,`jobapp_interviewer_name`,`jobapp_phone`,`jobapp_birthdate`,`jobapp_created_at`, IF(epe.`exp_designation`=\"NA\",'Fresher','Experienced') AS experience,`jobapp_address_for_correspondance1`,\n"
            + "`jobapp_city`,`jobapp_reffered_by_name`,`jobapp_time_required_for_joining_if_select`,`skill_qualification`,`skill_college_uni_institute_name`,`skill_examination`,`skill_year_of_passing`,`jobapp_resume_file_path`,\n"
            + "`resumes_master_id`,jp.status,jp.salary_bifurcation,jp.offer_letter_status,jp.loi_status,jp.jobapp_consultancy_walkin_name,jp.jobapp_email,jp.department,jp.work_location\n"
            + "FROM`job_applicants` AS jp INNER JOIN `emp_previous_experice` AS epe ON jp.`jobapp_id`=epe.`exp_jobapp_id`\n"
            + "INNER JOIN `emp_skills` AS es ON jp.`jobapp_id`=es.skill_jobapp_id\n"
            + "WHERE jobapp_id=?1\n"
            + "GROUP BY exp_jobapp_id", nativeQuery = true)
    public List getAllNewApplications(Integer jAppId);

    @Query(value = "SELECT `jobapp_resume_file_path`FROM`job_applicants`WHERE jobapp_id=?1", nativeQuery = true)
    public String getFilePath(Integer jAppId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE `job_applicants` SET STATUS=?2,feedback_count=?3,next_round=?4,offered_salary=?5 WHERE jobapp_id=?1", nativeQuery = true)
    public void updateStatus(Integer applicantId, String finalStatus, Integer feedCount, Integer nxtRound,String OffrSal);

    //HR Dashboard Count
    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE  STATUS='FRESH'", nativeQuery = true)
    public int HRCountFresh(Integer hrId);

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE  STATUS='NxtRound' and next_round=?1", nativeQuery = true)
    public int CountNxtRound(Integer hrId);

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE  STATUS='ClientRound' and next_round=?1", nativeQuery = true)
    public int CountClientRound(Integer hrId);

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE STATUS='SELECTED'", nativeQuery = true)
    public int HRCountSelected();

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE STATUS='REJECTED'", nativeQuery = true)
    public int HRCountRejected();

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE  STATUS='nxtRound' and next_round=?1", nativeQuery = true)
    public Object TLCountFresh(Integer hrId);

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE STATUS='HOLD'", nativeQuery = true)
    public Object HRCountHold();

    @Query(value = "SELECT jp.`jobapp_id`,`jobapp_interviewer_name`,`jobapp_phone`,`jobapp_birthdate`,`jobapp_created_at`, IF(epe.`exp_designation`='NA','Fresher','Experienced') AS experience,\n"
            + "`jobapp_address_for_correspondance1`,`jobapp_city`,`jobapp_reffered_by_name`,`jobapp_time_required_for_joining_if_select`,`skill_qualification`,`skill_college_uni_institute_name`,`skill_examination`,`skill_year_of_passing`,`jobapp_resume_file_path`,jp.STATUS\n"
            + "FROM`job_applicants` AS jp INNER JOIN `emp_previous_experice` AS epe ON jp.`jobapp_id`=epe.`exp_jobapp_id`\n"
            + "INNER JOIN `emp_skills` AS es ON jp.`jobapp_id`=es.skill_jobapp_id\n"
            + "WHERE jp.jobapp_created_at BETWEEN ?1 AND ?2\n"
            + "GROUP BY exp_jobapp_id", nativeQuery = true)
    public List<Object[]> getExport(String toDate, String fromDate);

    @Query(value = "SELECT DATE_FORMAT(jobapp_created_at,'%M') FROM `job_applicants` WHERE `jobapp_created_at` >= NOW()-INTERVAL 3 MONTH GROUP BY MONTH(jobapp_created_at)", nativeQuery = true)
    public ArrayList<String> getLast3Months();
//    @Query(value = "SELECT DATE_FORMAT(jobapp_created_at,'%M'), COUNT(`jobapp_id`) FROM `job_applicants` WHERE `jobapp_created_at` >= NOW()-INTERVAL 3 MONTH GROUP BY MONTH(jobapp_created_at)",nativeQuery = true)
//    public ArrayList<String> getAllLast3Months();

    @Query(value = "SELECT DATE_FORMAT(jobapp_created_at,'%M'), COUNT(`jobapp_id`) FROM `job_applicants` WHERE `status`='SELECTED' AND`jobapp_created_at` >= NOW()-INTERVAL 3 MONTH GROUP BY MONTH(jobapp_created_at)", nativeQuery = true)
    public List<Object[]> getSelectedLast3Months();

    @Query(value = "SELECT DATE_FORMAT(jobapp_created_at,'%M'), COUNT(`jobapp_id`) FROM `job_applicants` WHERE `status`='REJECTED' AND`jobapp_created_at` >= NOW()-INTERVAL 3 MONTH GROUP BY MONTH(jobapp_created_at)", nativeQuery = true)
    public List<Object[]> getRejectedLast3Months();

    @Query(value = "SELECT DATE_FORMAT(jobapp_created_at,'%M'), COUNT(`jobapp_id`) FROM `job_applicants` WHERE `jobapp_created_at` >= NOW()-INTERVAL 3 MONTH GROUP BY MONTH(jobapp_created_at)", nativeQuery = true)
    public List<Object[]> getAllLast3Months();

    @Query(value = "SELECT STATUS,COUNT(jobapp_id) * 100/ SUM(COUNT(jobapp_id)) OVER() 'Percentage(%)' FROM `job_applicants` GROUP BY STATUS ORDER BY STATUS ASC", nativeQuery = true)
    public List<Object[]> getOverAllPerData();

//    @Query(value = "SELECT `jobapp_id`,`jobapp_interviewer_name`FROM `job_applicants` WHERE STATUS='SELECTED'  ORDER BY jobapp_interviewer_name ASC", nativeQuery = true)
//    public List getSelectedAgentFortraining();
//
//    @Query(value = "SELECT `jobapp_interviewer_name`FROM `job_applicants` WHERE `jobapp_id` IN(?1)  ORDER BY jobapp_interviewer_name ASC", nativeQuery = true)
//    public List getAgentNamesList(String agentList);
//
//    @Query(value = "SELECT `jobapp_interviewer_name`FROM `job_applicants` WHERE `jobapp_id` IN(?1)  ORDER BY jobapp_interviewer_name ASC", nativeQuery = true)
//    public List getAgentNamesList(List<String> myList);
    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE STATUS='FRESH'", nativeQuery = true)
    public int adminCountFresh();

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE STATUS='NxtRound'", nativeQuery = true)
    public int adminCountNxtRound();

    @Query(value = "SELECT COUNT(*) FROM `job_applicants` WHERE STATUS='ClientRound'", nativeQuery = true)
    public int adminCountClientRound();

    @Query(value = "SELECT jp.`jobapp_id`,`jobapp_interviewer_name`,`jobapp_phone`,`jobapp_birthdate`,`jobapp_created_at`, IF(epe.`exp_designation`=\"NA\",'Fresher','Experienced') AS experience,STATUS\n"
            + "FROM`job_applicants` AS jp INNER JOIN `emp_previous_experice` AS epe ON jp.`jobapp_id`=epe.`exp_jobapp_id`\n"
            + "WHERE STATUS=?1 GROUP BY exp_jobapp_id ORDER BY jobapp_created_at ASC", nativeQuery = true)
    public List getAllNewApplications(String status, Integer id);
}
