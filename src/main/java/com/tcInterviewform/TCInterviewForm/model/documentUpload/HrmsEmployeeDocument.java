/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model.documentUpload;

import com.tcInterviewform.TCInterviewForm.model.HrmsEmployeeMaster;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 *
 * @author teamd
 */
@Entity
@Table(name = "hrms_employee_documents")
public class HrmsEmployeeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_master_id")
    private HrmsEmployeeMaster employeeMaster;

    @Column(length = 100)
    private String documentType;

    @Column(length = 255)
    private String fileName;

    @Column(length = 500,name = "s3_path")
    private String s3Path;

    @Column(length = 30)
    private String status;

    private LocalDateTime uploadedAt;

    @Column(length = 100)
    private String verifiedBy;

    private LocalDateTime verifiedAt;

    @Column(length = 30)
    private String verificationStatus;

    @Column(columnDefinition = "TEXT")
    private String remark;

    @PrePersist
    public void onCreate() {

        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }

        if (status == null) {
            status = "UPLOADED";
        }

        if (verificationStatus == null) {
            verificationStatus = "PENDING";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HrmsEmployeeMaster getEmployeeMaster() {
        return employeeMaster;
    }

    public void setEmployeeMaster(HrmsEmployeeMaster employeeMaster) {
        this.employeeMaster = employeeMaster;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getS3Path() {
        return s3Path;
    }

    public void setS3Path(String s3Path) {
        this.s3Path = s3Path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
