/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.repository;

import com.tcInterviewform.TCInterviewForm.model.MetaAddsLead;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author teamd
 */
@Repository
public interface MetaAddsLeadRepository extends JpaRepository<MetaAddsLead, Long> {
//    WHERE`status`=?1

    @Query(value = "SELECT id, `updated_at`,`name`,`email`,`phone_number`,`location`,`language`,status,adset_name FROM`meta_adds_lead` where status=?1 ORDER BY updated_at desc", nativeQuery = true)
    public List GetAddsLeadData(String status);

    @Query(value = "SELECT id, `created_at`,`name`,`email`,`phone_number`,`location`,`language`,status,adset_name,campaign_name,form_name FROM`meta_adds_lead` WHERE`id`=?1", nativeQuery = true)
    public List getMetaAddsLeadDetails(Integer leadId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `meta_adds_lead` SET`status`=?2 WHERE `id`=?1", nativeQuery = true)
    public int updateCallStatus(Integer leadId, String status);

    @Query("SELECT m.phoneNumber FROM MetaAddsLead m WHERE m.phoneNumber IN :phoneNo")
    List<String> findExistingPhoneNo(@Param("phoneNo") List<String> phoneNo);
//    @Query("SELECT m.email FROM MetaAddsLead m WHERE m.email IN :emails")
//List<String> findExistingEmails(@Param("emails") List<String> emails);

    @Query(value = "SELECT email FROM meta_adds_lead  WHERE `id`=?1", nativeQuery = true)
    public String getApplicantMailIDByLeadId(Integer leadId);

    @Query(value = "SELECT id FROM meta_adds_lead WHERE id > ?1 ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Integer findNextLeadId(Integer currentId);

    @Query(value = "SELECT \n"
            + "    ml.id,\n"
            + "    ml.created_at,\n"
            + "    ml.name,\n"
            + "    ml.email,\n"
            + "    ml.phone_number,\n"
            + "    ml.location,\n"
            + "    ml.adset_name,\n"
            + "    ml.campaign_name,\n"
            + "    ml.form_name,\n"
            + "    ml.status,\n"
            + "    mf.call_status,\n"
            + "    mf.action_by,\n"
            + "    mf.language,\n"
            + "    mf.salary_expectation,\n"
            + "    mf.feedback AS latest_feedback,\n"
            + "    COALESCE(fc.total_count, 0) AS total_followups\n"
            + "FROM meta_adds_lead ml\n"
            + "LEFT JOIN meta_adds_followup mf \n"
            + "    ON mf.id = (SELECT MAX(id)\n"
            + "        FROM meta_adds_followup\n"
            + "        WHERE lead_id = ml.id)\n"
            + "LEFT JOIN (\n"
            + "    SELECT lead_id, COUNT(*) AS total_count\n"
            + "    FROM meta_adds_followup\n"
            + "    GROUP BY lead_id\n"
            + ") fc ON ml.id = fc.lead_id\n"
            + "WHERE \n"
            + "    ml.created_at BETWEEN ?1 AND ?2", nativeQuery = true)
    public List<Object[]> getMetaAddsExport(String toDate, String fromDate, String status);

}
