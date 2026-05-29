/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author SIF
 */
@Entity
@Table(name = "job_applicants")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JobApplicants.findAll", query = "SELECT j FROM JobApplicants j")
    , @NamedQuery(name = "JobApplicants.findByJobappId", query = "SELECT j FROM JobApplicants j WHERE j.jobappId = :jobappId")
    , @NamedQuery(name = "JobApplicants.findByJobappAge", query = "SELECT j FROM JobApplicants j WHERE j.jobappAge = :jobappAge")
    , @NamedQuery(name = "JobApplicants.findByJobappAltPhone", query = "SELECT j FROM JobApplicants j WHERE j.jobappAltPhone = :jobappAltPhone")
    , @NamedQuery(name = "JobApplicants.findByJobappBirthdate", query = "SELECT j FROM JobApplicants j WHERE j.jobappBirthdate = :jobappBirthdate")
    , @NamedQuery(name = "JobApplicants.findByJobappBirthplace", query = "SELECT j FROM JobApplicants j WHERE j.jobappBirthplace = :jobappBirthplace")
    , @NamedQuery(name = "JobApplicants.findByJobappCity", query = "SELECT j FROM JobApplicants j WHERE j.jobappCity = :jobappCity")
    , @NamedQuery(name = "JobApplicants.findByJobappConsultancyWalkinName", query = "SELECT j FROM JobApplicants j WHERE j.jobappConsultancyWalkinName = :jobappConsultancyWalkinName")
    , @NamedQuery(name = "JobApplicants.findByJobappCreatedAt", query = "SELECT j FROM JobApplicants j WHERE j.jobappCreatedAt = :jobappCreatedAt")
    , @NamedQuery(name = "JobApplicants.findByJobappEmail", query = "SELECT j FROM JobApplicants j WHERE j.jobappEmail = :jobappEmail")
    , @NamedQuery(name = "JobApplicants.findByJobappFinalStatus", query = "SELECT j FROM JobApplicants j WHERE j.jobappFinalStatus = :jobappFinalStatus")
    , @NamedQuery(name = "JobApplicants.findByJobappGender", query = "SELECT j FROM JobApplicants j WHERE j.jobappGender = :jobappGender")
    , @NamedQuery(name = "JobApplicants.findByJobappHrRemark", query = "SELECT j FROM JobApplicants j WHERE j.jobappHrRemark = :jobappHrRemark")
    , @NamedQuery(name = "JobApplicants.findByJobappInterviewerName", query = "SELECT j FROM JobApplicants j WHERE j.jobappInterviewerName = :jobappInterviewerName")
    , @NamedQuery(name = "JobApplicants.findByJobappInteviewVenue", query = "SELECT j FROM JobApplicants j WHERE j.jobappInteviewVenue = :jobappInteviewVenue")
    , @NamedQuery(name = "JobApplicants.findByJobappMaritalStatus", query = "SELECT j FROM JobApplicants j WHERE j.jobappMaritalStatus = :jobappMaritalStatus")
    , @NamedQuery(name = "JobApplicants.findByJobappNationality", query = "SELECT j FROM JobApplicants j WHERE j.jobappNationality = :jobappNationality")
    , @NamedQuery(name = "JobApplicants.findByJobappPhone", query = "SELECT j FROM JobApplicants j WHERE j.jobappPhone = :jobappPhone")
    , @NamedQuery(name = "JobApplicants.findByJobappPostalCode", query = "SELECT j FROM JobApplicants j WHERE j.jobappPostalCode = :jobappPostalCode")
    , @NamedQuery(name = "JobApplicants.findByJobappRefferedByName", query = "SELECT j FROM JobApplicants j WHERE j.jobappRefferedByName = :jobappRefferedByName")
    , @NamedQuery(name = "JobApplicants.findByJobappState", query = "SELECT j FROM JobApplicants j WHERE j.jobappState = :jobappState")
    , @NamedQuery(name = "JobApplicants.findByJobappStatus", query = "SELECT j FROM JobApplicants j WHERE j.jobappStatus = :jobappStatus")
    , @NamedQuery(name = "JobApplicants.findByJobappTimeRequiredForJoiningIfSelect", query = "SELECT j FROM JobApplicants j WHERE j.jobappTimeRequiredForJoiningIfSelect = :jobappTimeRequiredForJoiningIfSelect")
    , @NamedQuery(name = "JobApplicants.findByJobappUpdatedAt", query = "SELECT j FROM JobApplicants j WHERE j.jobappUpdatedAt = :jobappUpdatedAt")
    , @NamedQuery(name = "JobApplicants.findByJobappResumeFilePath", query = "SELECT j FROM JobApplicants j WHERE j.jobappResumeFilePath = :jobappResumeFilePath")
    , @NamedQuery(name = "JobApplicants.findByJobappJobId", query = "SELECT j FROM JobApplicants j WHERE j.jobappJobId = :jobappJobId")
    , @NamedQuery(name = "JobApplicants.findByJobappJtId", query = "SELECT j FROM JobApplicants j WHERE j.jobappJtId = :jobappJtId")
    , @NamedQuery(name = "JobApplicants.findByJobappOtpId", query = "SELECT j FROM JobApplicants j WHERE j.jobappOtpId = :jobappOtpId")})
public class JobApplicants implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "jobapp_id")
    private Integer jobappId;
    @Lob
    @Column(name = "jobapp_address_for_correspondance1")
    private String jobappAddressForCorrespondance1;
    @Lob
    @Column(name = "jobapp_address_for_correspondance2")
    private String jobappAddressForCorrespondance2;
    @Column(name = "jobapp_age")
    private Integer jobappAge;
    @Column(name = "jobapp_alt_phone")
    private String jobappAltPhone;
    @Column(name = "jobapp_birthdate")
    @Temporal(TemporalType.DATE)
    private Date jobappBirthdate;
    @Column(name = "jobapp_birthplace")
    private String jobappBirthplace;
    @Column(name = "jobapp_city")
    private String jobappCity;
    @Column(name = "jobapp_consultancy_walkin_name")
    private String jobappConsultancyWalkinName;
    @CreationTimestamp
    @Column(name = "jobapp_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobappCreatedAt;
    @Column(name = "jobapp_email")
    private String jobappEmail;
    @Column(name = "jobapp_final_status")
    private String jobappFinalStatus;
    @Column(name = "jobapp_gender")
    private String jobappGender;
    @Column(name = "jobapp_hr_remark")
    private String jobappHrRemark;
    @Column(name = "jobapp_interviewer_name")
    private String jobappInterviewerName;
    @Column(name = "jobapp_inteview_venue")
    private String jobappInteviewVenue;
    @Lob
    @Column(name = "jobapp_languages_known")
    private String jobappLanguagesKnown;
    @Column(name = "jobapp_marital_status")
    private String jobappMaritalStatus;
    @Column(name = "jobapp_nationality")
    private String jobappNationality;
    @Column(name = "jobapp_phone")
    private String jobappPhone;
    @Column(name = "jobapp_postal_code")
    private String jobappPostalCode;
    @Column(name = "jobapp_reffered_by_name")
    private String jobappRefferedByName;
    @Column(name = "jobapp_state")
    private String jobappState;
    @Column(name = "jobapp_status")
    private Short jobappStatus;
    @Column(name = "jobapp_time_required_for_joining_if_select")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate jobappTimeRequiredForJoiningIfSelect;
    @UpdateTimestamp
    @Column(name = "jobapp_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobappUpdatedAt;
    @Column(name = "jobapp_resume_file_path")
    private String jobappResumeFilePath;
    @Column(name = "jobapp_job_id")
    private Integer jobappJobId;
    @Column(name = "jobapp_jt_id")
    private Integer jobappJtId;
    @Column(name = "jobapp_otp_id")
    private Integer jobappOtpId;
    @Column(name = "status")
    private String status;

    //added by Nitin
    @Column(name = "resumes_master_id")
    private Integer resumesMasterId;

    @Column(name = "feedback_count")
    private Integer feedbackCount;
    @Column(name = "next_round")
    private Integer nextRound;
    @Column(name = "salary_bifurcation")
    private Integer salaryBifurcation;
    @Column(name = "offer_letter_status")
    private String offerLetterStatus;
    @Column(name = "loi_status")
    private String loiStatus;
    @Column(name = "department")
    private String department;
    @Column(name = "work_location")
    private String workLocation;
    @Column(name = "offered_salary")
    private String offeredSalary;

    public String getOfferedSalary() {
        return offeredSalary;
    }

    public void setOfferedSalary(String offeredSalary) {
        this.offeredSalary = offeredSalary;
    }
 
    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getNextRound() {
        return nextRound;
    }

    public void setNextRound(Integer nextRound) {
        this.nextRound = nextRound;
    }

    

    public Integer getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(Integer feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public JobApplicants() {
    }

    public JobApplicants(Integer jobappId) {
        this.jobappId = jobappId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getJobappId() {
        return jobappId;
    }

    public void setJobappId(Integer jobappId) {
        this.jobappId = jobappId;
    }

    public String getJobappAddressForCorrespondance1() {
        return jobappAddressForCorrespondance1;
    }

    public void setJobappAddressForCorrespondance1(String jobappAddressForCorrespondance1) {
        this.jobappAddressForCorrespondance1 = jobappAddressForCorrespondance1;
    }

    public String getJobappAddressForCorrespondance2() {
        return jobappAddressForCorrespondance2;
    }

    public void setJobappAddressForCorrespondance2(String jobappAddressForCorrespondance2) {
        this.jobappAddressForCorrespondance2 = jobappAddressForCorrespondance2;
    }

    public Integer getJobappAge() {
        return jobappAge;
    }

    public void setJobappAge(Integer jobappAge) {
        this.jobappAge = jobappAge;
    }

    public String getJobappAltPhone() {
        return jobappAltPhone;
    }

    public void setJobappAltPhone(String jobappAltPhone) {
        this.jobappAltPhone = jobappAltPhone;
    }

    public Date getJobappBirthdate() {
        return jobappBirthdate;
    }

    public void setJobappBirthdate(Date jobappBirthdate) {
        this.jobappBirthdate = jobappBirthdate;
    }

    public String getJobappBirthplace() {
        return jobappBirthplace;
    }

    public void setJobappBirthplace(String jobappBirthplace) {
        this.jobappBirthplace = jobappBirthplace;
    }

    public String getJobappCity() {
        return jobappCity;
    }

    public void setJobappCity(String jobappCity) {
        this.jobappCity = jobappCity;
    }

    public String getJobappConsultancyWalkinName() {
        return jobappConsultancyWalkinName;
    }

    public void setJobappConsultancyWalkinName(String jobappConsultancyWalkinName) {
        this.jobappConsultancyWalkinName = jobappConsultancyWalkinName;
    }

    public Date getJobappCreatedAt() {
        return jobappCreatedAt;
    }

    public void setJobappCreatedAt(Date jobappCreatedAt) {
        this.jobappCreatedAt = jobappCreatedAt;
    }

    public String getJobappEmail() {
        return jobappEmail;
    }

    public void setJobappEmail(String jobappEmail) {
        this.jobappEmail = jobappEmail;
    }

    public String getJobappFinalStatus() {
        return jobappFinalStatus;
    }

    public void setJobappFinalStatus(String jobappFinalStatus) {
        this.jobappFinalStatus = jobappFinalStatus;
    }

    public String getJobappGender() {
        return jobappGender;
    }

    public void setJobappGender(String jobappGender) {
        this.jobappGender = jobappGender;
    }

    public String getJobappHrRemark() {
        return jobappHrRemark;
    }

    public void setJobappHrRemark(String jobappHrRemark) {
        this.jobappHrRemark = jobappHrRemark;
    }

    public String getJobappInterviewerName() {
        return jobappInterviewerName;
    }

    public void setJobappInterviewerName(String jobappInterviewerName) {
        this.jobappInterviewerName = jobappInterviewerName;
    }

    public String getJobappInteviewVenue() {
        return jobappInteviewVenue;
    }

    public void setJobappInteviewVenue(String jobappInteviewVenue) {
        this.jobappInteviewVenue = jobappInteviewVenue;
    }

    public String getJobappLanguagesKnown() {
        return jobappLanguagesKnown;
    }

    public void setJobappLanguagesKnown(String jobappLanguagesKnown) {
        this.jobappLanguagesKnown = jobappLanguagesKnown;
    }

    public String getJobappMaritalStatus() {
        return jobappMaritalStatus;
    }

    public void setJobappMaritalStatus(String jobappMaritalStatus) {
        this.jobappMaritalStatus = jobappMaritalStatus;
    }

    public String getJobappNationality() {
        return jobappNationality;
    }

    public void setJobappNationality(String jobappNationality) {
        this.jobappNationality = jobappNationality;
    }

    public String getJobappPhone() {
        return jobappPhone;
    }

    public void setJobappPhone(String jobappPhone) {
        this.jobappPhone = jobappPhone;
    }

    public String getJobappPostalCode() {
        return jobappPostalCode;
    }

    public void setJobappPostalCode(String jobappPostalCode) {
        this.jobappPostalCode = jobappPostalCode;
    }

    public String getJobappRefferedByName() {
        return jobappRefferedByName;
    }

    public void setJobappRefferedByName(String jobappRefferedByName) {
        this.jobappRefferedByName = jobappRefferedByName;
    }

    public String getJobappState() {
        return jobappState;
    }

    public void setJobappState(String jobappState) {
        this.jobappState = jobappState;
    }

    public Short getJobappStatus() {
        return jobappStatus;
    }

    public void setJobappStatus(Short jobappStatus) {
        this.jobappStatus = jobappStatus;
    }

    public LocalDate getJobappTimeRequiredForJoiningIfSelect() {
        return jobappTimeRequiredForJoiningIfSelect;
    }

//    public Date getJobappTimeRequiredForJoiningIfSelect() {
//        return jobappTimeRequiredForJoiningIfSelect;
//    }
//
//    public void setJobappTimeRequiredForJoiningIfSelect(Date jobappTimeRequiredForJoiningIfSelect) {
//        this.jobappTimeRequiredForJoiningIfSelect = jobappTimeRequiredForJoiningIfSelect;
//    }
    public void setJobappTimeRequiredForJoiningIfSelect(LocalDate jobappTimeRequiredForJoiningIfSelect) {
        this.jobappTimeRequiredForJoiningIfSelect = jobappTimeRequiredForJoiningIfSelect;
    }

    public Date getJobappUpdatedAt() {
        return jobappUpdatedAt;
    }

    public void setJobappUpdatedAt(Date jobappUpdatedAt) {
        this.jobappUpdatedAt = jobappUpdatedAt;
    }

    public String getJobappResumeFilePath() {
        return jobappResumeFilePath;
    }

    public void setJobappResumeFilePath(String jobappResumeFilePath) {
        this.jobappResumeFilePath = jobappResumeFilePath;
    }

    public Integer getJobappJobId() {
        return jobappJobId;
    }

    public void setJobappJobId(Integer jobappJobId) {
        this.jobappJobId = jobappJobId;
    }

    public Integer getJobappJtId() {
        return jobappJtId;
    }

    public void setJobappJtId(Integer jobappJtId) {
        this.jobappJtId = jobappJtId;
    }

    public Integer getJobappOtpId() {
        return jobappOtpId;
    }

    public void setJobappOtpId(Integer jobappOtpId) {
        this.jobappOtpId = jobappOtpId;
    }

    public Integer getResumesMasterId() {
        return resumesMasterId;
    }

    public void setResumesMasterId(Integer resumesMasterId) {
        this.resumesMasterId = resumesMasterId;
    }

    public Integer getSalaryBifurcation() {
        return salaryBifurcation;
    }

    public void setSalaryBifurcation(Integer salaryBifurcation) {
        this.salaryBifurcation = salaryBifurcation;
    }

    public String getOfferLetterStatus() {
        return offerLetterStatus;
    }

    public void setOfferLetterStatus(String offerLetterStatus) {
        this.offerLetterStatus = offerLetterStatus;
    }

    public String getLoiStatus() {
        return loiStatus;
    }

    public void setLoiStatus(String loiStatus) {
        this.loiStatus = loiStatus;
    }


    @Override
    public String toString() {
        return "JobApplicants{" + "jobappId=" + jobappId + ", jobappAddressForCorrespondance1=" + jobappAddressForCorrespondance1 + ", jobappAddressForCorrespondance2=" + jobappAddressForCorrespondance2 + ", jobappAge=" + jobappAge + ", jobappAltPhone=" + jobappAltPhone + ", jobappBirthdate=" + jobappBirthdate + ", jobappBirthplace=" + jobappBirthplace + ", jobappCity=" + jobappCity + ", jobappConsultancyWalkinName=" + jobappConsultancyWalkinName + ", jobappCreatedAt=" + jobappCreatedAt + ", jobappEmail=" + jobappEmail + ", jobappFinalStatus=" + jobappFinalStatus + ", jobappGender=" + jobappGender + ", jobappHrRemark=" + jobappHrRemark + ", jobappInterviewerName=" + jobappInterviewerName + ", jobappInteviewVenue=" + jobappInteviewVenue + ", jobappLanguagesKnown=" + jobappLanguagesKnown + ", jobappMaritalStatus=" + jobappMaritalStatus + ", jobappNationality=" + jobappNationality + ", jobappPhone=" + jobappPhone + ", jobappPostalCode=" + jobappPostalCode + ", jobappRefferedByName=" + jobappRefferedByName + ", jobappState=" + jobappState + ", jobappStatus=" + jobappStatus + ", jobappTimeRequiredForJoiningIfSelect=" + jobappTimeRequiredForJoiningIfSelect + ", jobappUpdatedAt=" + jobappUpdatedAt + ", jobappResumeFilePath=" + jobappResumeFilePath + ", jobappJobId=" + jobappJobId + ", jobappJtId=" + jobappJtId + ", jobappOtpId=" + jobappOtpId + ", status=" + status + ", resumesMasterId=" + resumesMasterId + '}';
    }

}
