/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model;

import java.io.Serializable;
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

/**
 *
 * @author SIF
 */
@Entity
@Table(name = "hr_feedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HrFeedback.findAll", query = "SELECT h FROM HrFeedback h")
    , @NamedQuery(name = "HrFeedback.findById", query = "SELECT h FROM HrFeedback h WHERE h.id = :id")
    , @NamedQuery(name = "HrFeedback.findByCreatedAt", query = "SELECT h FROM HrFeedback h WHERE h.createdAt = :createdAt")
    , @NamedQuery(name = "HrFeedback.findByUpdatedAt", query = "SELECT h FROM HrFeedback h WHERE h.updatedAt = :updatedAt")
    , @NamedQuery(name = "HrFeedback.findByApplicantId", query = "SELECT h FROM HrFeedback h WHERE h.applicantId = :applicantId")
    , @NamedQuery(name = "HrFeedback.findByFeedbackBy", query = "SELECT h FROM HrFeedback h WHERE h.feedbackBy = :feedbackBy")
    , @NamedQuery(name = "HrFeedback.findByTypingTest", query = "SELECT h FROM HrFeedback h WHERE h.typingTest = :typingTest")
    , @NamedQuery(name = "HrFeedback.findByWorkExperience", query = "SELECT h FROM HrFeedback h WHERE h.workExperience = :workExperience")
    , @NamedQuery(name = "HrFeedback.findByJobKnowledge", query = "SELECT h FROM HrFeedback h WHERE h.jobKnowledge = :jobKnowledge")
    , @NamedQuery(name = "HrFeedback.findByCommunicationSkills", query = "SELECT h FROM HrFeedback h WHERE h.communicationSkills = :communicationSkills")
    , @NamedQuery(name = "HrFeedback.findByMotivationInitiative", query = "SELECT h FROM HrFeedback h WHERE h.motivationInitiative = :motivationInitiative")
    , @NamedQuery(name = "HrFeedback.findByEducationBackground", query = "SELECT h FROM HrFeedback h WHERE h.educationBackground = :educationBackground")
    , @NamedQuery(name = "HrFeedback.findByPersonality", query = "SELECT h FROM HrFeedback h WHERE h.personality = :personality")
    , @NamedQuery(name = "HrFeedback.findByLanguage", query = "SELECT h FROM HrFeedback h WHERE h.language = :language")
    , @NamedQuery(name = "HrFeedback.findByOfferedSalary", query = "SELECT h FROM HrFeedback h WHERE h.offeredSalary = :offeredSalary")
    , @NamedQuery(name = "HrFeedback.findByExpectedSalary", query = "SELECT h FROM HrFeedback h WHERE h.expectedSalary = :expectedSalary")
    , @NamedQuery(name = "HrFeedback.findByCount", query = "SELECT h FROM HrFeedback h WHERE h.count = :count")
    , @NamedQuery(name = "HrFeedback.findByFinalStatus", query = "SELECT h FROM HrFeedback h WHERE h.finalStatus = :finalStatus")})
public class HrFeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "applicant_id")
    private Integer applicantId;
    @Column(name = "feedback_by")
    private String feedbackBy;
    @Column(name = "typing_test")
    private Integer typingTest;
    @Column(name = "work_experience")
    private Integer workExperience;
    @Column(name = "job_knowledge")
    private Integer jobKnowledge;
    @Column(name = "communication_skills")
    private Integer communicationSkills;
    @Column(name = "motivation_initiative")
    private Integer motivationInitiative;
    @Column(name = "education_background")
    private String educationBackground;
    @Column(name = "personality")
    private Integer personality;
    @Column(name = "language")
    private String language;
    @Column(name = "offered_salary")
    private String offeredSalary;
    @Column(name = "expected_salary")
    private String expectedSalary;
    @Lob
    @Column(name = "remark")
    private String remark;
    @Column(name = "count")
    private Integer count;
    @Column(name = "final_status")
    private String finalStatus;
    @Column(name = "interviewer_name")
    private String interviewerName;
    
    @Column(name = "feedback_attempt")
    private Integer feedbackAttempt;
    @Column(name = "next_round")
    private Integer assignTo;
    @Column(name = "previous_salary")
    private String previousSalary;
    @Column(name = "client_name")
    private String clientName;
    @Column(name = "process_name")
    private String processName;
    @Column(name = "feedback_received")
    private String feedbackReceived;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getFeedbackReceived() {
        return feedbackReceived;
    }

    public void setFeedbackReceived(String feedbackReceived) {
        this.feedbackReceived = feedbackReceived;
    }

    public String getPreviousSalary() {
        return previousSalary;
    }

    public void setPreviousSalary(String previousSalary) {
        this.previousSalary = previousSalary;
    }

    public Integer getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(Integer assignTo) {
        this.assignTo = assignTo;
    }

    
    public Integer getFeedbackAttempt() {
        return feedbackAttempt;
    }

    public void setFeedbackAttempt(Integer feedbackAttempt) {
        this.feedbackAttempt = feedbackAttempt;
    }
    

    public HrFeedback() {
    }

    public HrFeedback(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public String getFeedbackBy() {
        return feedbackBy;
    }

    public void setFeedbackBy(String feedbackBy) {
        this.feedbackBy = feedbackBy;
    }

    public Integer getTypingTest() {
        return typingTest;
    }

    public void setTypingTest(Integer typingTest) {
        this.typingTest = typingTest;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public Integer getJobKnowledge() {
        return jobKnowledge;
    }

    public void setJobKnowledge(Integer jobKnowledge) {
        this.jobKnowledge = jobKnowledge;
    }

    public Integer getCommunicationSkills() {
        return communicationSkills;
    }

    public void setCommunicationSkills(Integer communicationSkills) {
        this.communicationSkills = communicationSkills;
    }

    public Integer getMotivationInitiative() {
        return motivationInitiative;
    }

    public void setMotivationInitiative(Integer motivationInitiative) {
        this.motivationInitiative = motivationInitiative;
    }

   

    public Integer getPersonality() {
        return personality;
    }

    public void setPersonality(Integer personality) {
        this.personality = personality;
    }

  

    public String getOfferedSalary() {
        return offeredSalary;
    }

    public void setOfferedSalary(String offeredSalary) {
        this.offeredSalary = offeredSalary;
    }

    public String getExpectedSalary() {
        return expectedSalary;
    }

    public void setExpectedSalary(String expectedSalary) {
        this.expectedSalary = expectedSalary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(String finalStatus) {
        this.finalStatus = finalStatus;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "HrFeedback{" + "id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", applicantId=" + applicantId + ", feedbackBy=" + feedbackBy + ", typingTest=" + typingTest + ", workExperience=" + workExperience + ", jobKnowledge=" + jobKnowledge + ", communicationSkills=" + communicationSkills + ", motivationInitiative=" + motivationInitiative + ", educationBackground=" + educationBackground + ", personality=" + personality + ", language=" + language + ", offeredSalary=" + offeredSalary + ", expectedSalary=" + expectedSalary + ", remark=" + remark + ", count=" + count + ", finalStatus=" + finalStatus + ", interviewerName=" + interviewerName + '}';
    }

   
    
}
