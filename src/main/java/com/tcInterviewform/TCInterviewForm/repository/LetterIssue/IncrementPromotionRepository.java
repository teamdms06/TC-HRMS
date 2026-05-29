///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.repository.LetterIssue;
//
//import com.tcInterviewform.TCInterviewForm.model.LetterIssue.IncrementPromotion;
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
//public interface IncrementPromotionRepository extends JpaRepository<IncrementPromotion, Long> {
//  
//    @Query(value = "SELECT * FROM increment_promotion WHERE type=?1 and status = ?2",nativeQuery = true)
//    public List<IncrementPromotion> findByTypeAndStatus(String type, String status);
//}
