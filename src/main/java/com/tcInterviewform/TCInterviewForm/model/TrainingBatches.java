///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.model;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Lob;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.xml.bind.annotation.XmlRootElement;
//import org.hibernate.annotations.CreationTimestamp;
//
///**
// *
// * @author SIF
// */
//@Entity
//@Table(name = "training_batches")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "TrainingBatches.findAll", query = "SELECT t FROM TrainingBatches t")
//    , @NamedQuery(name = "TrainingBatches.findById", query = "SELECT t FROM TrainingBatches t WHERE t.id = :id")
//    , @NamedQuery(name = "TrainingBatches.findByCreatedAt", query = "SELECT t FROM TrainingBatches t WHERE t.createdAt = :createdAt")
//    , @NamedQuery(name = "TrainingBatches.findByBatchName", query = "SELECT t FROM TrainingBatches t WHERE t.batchName = :batchName")
//    , @NamedQuery(name = "TrainingBatches.findByBatchStartDate", query = "SELECT t FROM TrainingBatches t WHERE t.batchStartDate = :batchStartDate")
//    , @NamedQuery(name = "TrainingBatches.findByTrainerName", query = "SELECT t FROM TrainingBatches t WHERE t.trainerName = :trainerName")
//    , @NamedQuery(name = "TrainingBatches.findByLocation", query = "SELECT t FROM TrainingBatches t WHERE t.location = :location")
//    , @NamedQuery(name = "TrainingBatches.findByBatchStatus", query = "SELECT t FROM TrainingBatches t WHERE t.batchStatus = :batchStatus")
//    , @NamedQuery(name = "TrainingBatches.findByBatchComplateDate", query = "SELECT t FROM TrainingBatches t WHERE t.batchComplateDate = :batchComplateDate")})
//public class TrainingBatches implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Integer id;
//    @CreationTimestamp
//    @Column(name = "created_at",updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//    @Column(name = "batch_name")
//    private String batchName;
//    @Column(name = "batch_start_date")
//     private String batchStartDate;
//    @Column(name = "trainer_name")
//    private String trainerName;
//    @Column(name = "location")
//    private String location;
//    @Lob
//    @Column(name = "agent_list")
//    private String agentList;
//    @Column(name = "batch_status")
//    private String batchStatus;
//    @Column(name = "batch_complate_date")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date batchComplateDate;
//    @Lob
//    @Column(name = "remark")
//    private String remark;
//
//    public TrainingBatches() {
//    }
//
//    public TrainingBatches(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getBatchName() {
//        return batchName;
//    }
//
//    public void setBatchName(String batchName) {
//        this.batchName = batchName;
//    }
//
//    public String getBatchStartDate() {
//        return batchStartDate;
//    }
//
//    public void setBatchStartDate(String batchStartDate) {
//        this.batchStartDate = batchStartDate;
//    }
//
//    public String getTrainerName() {
//        return trainerName;
//    }
//
//    public void setTrainerName(String trainerName) {
//        this.trainerName = trainerName;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getAgentList() {
//        return agentList;
//    }
//
//    public void setAgentList(String agentList) {
//        this.agentList = agentList;
//    }
//
//    public String getBatchStatus() {
//        return batchStatus;
//    }
//
//    public void setBatchStatus(String batchStatus) {
//        this.batchStatus = batchStatus;
//    }
//
//    public Date getBatchComplateDate() {
//        return batchComplateDate;
//    }
//
//    public void setBatchComplateDate(Date batchComplateDate) {
//        this.batchComplateDate = batchComplateDate;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof TrainingBatches)) {
//            return false;
//        }
//        TrainingBatches other = (TrainingBatches) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "TrainingBatches{" + "id=" + id + ", createdAt=" + createdAt + ", batchName=" + batchName + ", batchStartDate=" + batchStartDate + ", trainerName=" + trainerName + ", location=" + location + ", agentList=" + agentList + ", batchStatus=" + batchStatus + ", batchComplateDate=" + batchComplateDate + ", remark=" + remark + '}';
//    }
//
// 
//    
//}
