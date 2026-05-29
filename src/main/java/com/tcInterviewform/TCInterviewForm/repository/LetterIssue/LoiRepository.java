///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.repository.LetterIssue;
//
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.Loi;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
///**
// *
// * @author teamd
// */
//@Repository
//public interface LoiRepository extends JpaRepository<Loi,Long>{
//    
//    @Query(value = "SELECT * FROM loi WHERE status = ?1",nativeQuery = true)
//     List<Loi> findByStatus(String status);
//}
