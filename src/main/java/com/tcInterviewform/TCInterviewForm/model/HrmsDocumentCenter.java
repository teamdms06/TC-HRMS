/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

/**
 *
 * @author teamd
 */
@Entity
@Table(name = "hrms_document_center")
public class HrmsDocumentCenter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_master_id")
    private HrmsEmployeeMaster employeeMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_id")
    private HrmsEmployeeSalary salary;

    private String documentType;

    private String documentNo;

    private String pdfPath;
 @Column(name = "s3_url")
    private String s3Url;

    private String status;

    private String mailStatus;

    private String generatedBy;

    private LocalDateTime generatedOn;

    private String sentBy;

    private LocalDateTime sentOn;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private String accepted_pdf_path;
    private String accepted_uploaded_by;
    private LocalDateTime accepted_uploaded_on;
    private String acceptance_status;
    

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (status == null || status.trim().isEmpty()) {
            status = "PENDING";
        }

        if (mailStatus == null || mailStatus.trim().isEmpty()) {
            mailStatus = "NOT_SENT";
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public HrmsDocumentCenter() {
    }

    public HrmsDocumentCenter(Long id) {
        this.id = id;
    }

    public HrmsDocumentCenter(Long id, String documentType) {
        this.id = id;
        this.documentType = documentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

   

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

 

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public HrmsEmployeeMaster getEmployeeMaster() {
        return employeeMaster;
    }

    public void setEmployeeMaster(HrmsEmployeeMaster employeeMaster) {
        this.employeeMaster = employeeMaster;
    }

    public HrmsEmployeeSalary getSalary() {
        return salary;
    }

    public void setSalary(HrmsEmployeeSalary salary) {
        this.salary = salary;
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(LocalDateTime generatedOn) {
        this.generatedOn = generatedOn;
    }

    public LocalDateTime getSentOn() {
        return sentOn;
    }

    public void setSentOn(LocalDateTime sentOn) {
        this.sentOn = sentOn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccepted_pdf_path() {
        return accepted_pdf_path;
    }

    public void setAccepted_pdf_path(String accepted_pdf_path) {
        this.accepted_pdf_path = accepted_pdf_path;
    }

    public String getAccepted_uploaded_by() {
        return accepted_uploaded_by;
    }

    public void setAccepted_uploaded_by(String accepted_uploaded_by) {
        this.accepted_uploaded_by = accepted_uploaded_by;
    }

    public LocalDateTime getAccepted_uploaded_on() {
        return accepted_uploaded_on;
    }

    public void setAccepted_uploaded_on(LocalDateTime accepted_uploaded_on) {
        this.accepted_uploaded_on = accepted_uploaded_on;
    }

    public String getAcceptance_status() {
        return acceptance_status;
    }

    public void setAcceptance_status(String acceptance_status) {
        this.acceptance_status = acceptance_status;
    }

   
    
}
