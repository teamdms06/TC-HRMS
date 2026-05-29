///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.repository;
//
//import com.tcInterviewform.TCInterviewForm.model.TrainingBatches;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
///**
// *
// * @author SIF
// */
//@Repository
//public interface TrainingBatchRepository extends JpaRepository<TrainingBatches, Integer> {
//
//    @Query(value = "SELECT id,`batch_name`,`batch_start_date`,`trainer_name`,`location`,`agent_list`,`batch_status` FROM `training_batches`", nativeQuery = true)
//    public List getBatchList();
//
//    @Query(value = "SELECT * FROM `training_batches` where id=?1", nativeQuery = true)
//    public List<TrainingBatches> findByBatchId(Integer batchId);
//
//    @Query(value = "SELECT * FROM `training_batches` where id=?1", nativeQuery = true)
//    public TrainingBatches findByBId(Integer batchId);
//
//}
