package com.tcInterviewform.TCInterviewForm.model.LetterIssue;

import com.tcInterviewform.TCInterviewForm.model.HrmsDocumentCenter;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author teamd
 */
@Entity
@Table(name = "hrms_warning_letter")
@Data
public class HrmsWarningLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_master_id")
    private HrmsEmployeeMaster employeeMaster;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_center_id")
    private HrmsDocumentCenter documentCenter;

    private String warningType;

    private String warningLevel;

    private String incidentReported;

    private LocalDateTime incidentDatetime;

    private String incidentReportedBy;

    @Column(columnDefinition = "TEXT")
    private String incidentDescription;

    private String explanationRequired;

    private Integer explanationHours;

    private String status;

    private String pdfPath;

    private String mailStatus;

    private String createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
