///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.repository.LetterIssue;
//
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.OldEmployeeDetails;
//import java.util.List;
//import javax.transaction.Transactional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
///**
// *
// * @author teamd
// */
//@Repository
//public interface OldEmployeeDetailsRepository extends JpaRepository<OldEmployeeDetails, Long> {
//
//    @Query(value = "SELECT id, `employee_id`,`employee_name`,`contact_number`,`date_of_joining`,`designation`,`offer_letter_status` FROM`old_employee_details`", nativeQuery = true)
//    public List getletterIssueOld(String status);
//
//    @Query(value = "SELECT id, `employee_id`,`employee_name`,`contact_number`,`date_of_joining`,`designation`,`offer_letter_status`,`date_of_birth`,`location`,`levels`,`education`,`email_id`,sal_bifurcation_id FROM`old_employee_details` WHERE`id`=?1", nativeQuery = true)
//    public List getOldEmployeeDeatailsById(Integer leadId);
//
//    boolean existsByEmployeeId(String employeeId);
//
//    @Query(value = "select * from old_employee_details where id=?1", nativeQuery = true)
//    public OldEmployeeDetails findOldEmployeeByIdId(Integer leadId);
//
//    @Modifying
//    @Transactional
//    @Query(value = "update old_employee_details set offer_letter_status=?2 where jobapp_id = ?1", nativeQuery = true)
//    public void updateOLStatus(Integer id, String status);
//
//}
