///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.model.LetterIssue;
//
//import java.io.Serializable;
//import java.time.LocalDate;
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
//import org.springframework.format.annotation.DateTimeFormat;
//
///**
// *
// * @author teamd
// */
//@Entity
//@Table(name = "loi")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "Loi.findAll", query = "SELECT l FROM Loi l")
//    , @NamedQuery(name = "Loi.findById", query = "SELECT l FROM Loi l WHERE l.id = :id")
//    , @NamedQuery(name = "Loi.findByEmployeeName", query = "SELECT l FROM Loi l WHERE l.employeeName = :employeeName")
//    , @NamedQuery(name = "Loi.findByDesignation", query = "SELECT l FROM Loi l WHERE l.designation = :designation")
//    , @NamedQuery(name = "Loi.findByDateOfJoining", query = "SELECT l FROM Loi l WHERE l.dateOfJoining = :dateOfJoining")
//    , @NamedQuery(name = "Loi.findByGender", query = "SELECT l FROM Loi l WHERE l.gender = :gender")
//    , @NamedQuery(name = "Loi.findByEmailId", query = "SELECT l FROM Loi l WHERE l.emailId = :emailId")
//    , @NamedQuery(name = "Loi.findByWorkLocation", query = "SELECT l FROM Loi l WHERE l.workLocation = :workLocation")
//    , @NamedQuery(name = "Loi.findByDepartment", query = "SELECT l FROM Loi l WHERE l.department = :department")
//    , @NamedQuery(name = "Loi.findByDocumentId", query = "SELECT l FROM Loi l WHERE l.documentId = :documentId")
//    , @NamedQuery(name = "Loi.findByGenerateOn", query = "SELECT l FROM Loi l WHERE l.generateOn = :generateOn")
//    , @NamedQuery(name = "Loi.findByGenerateBy", query = "SELECT l FROM Loi l WHERE l.generateBy = :generateBy")
//    , @NamedQuery(name = "Loi.findBySentOn", query = "SELECT l FROM Loi l WHERE l.sentOn = :sentOn")
//    , @NamedQuery(name = "Loi.findBySentBy", query = "SELECT l FROM Loi l WHERE l.sentBy = :sentBy")
//    , @NamedQuery(name = "Loi.findByStatus", query = "SELECT l FROM Loi l WHERE l.status = :status")
//    , @NamedQuery(name = "Loi.findByOfferedSalaray", query = "SELECT l FROM Loi l WHERE l.offeredSalaray = :offeredSalaray")})
//public class Loi implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Long id;
//    @Column(name = "employee_name")
//    private String employeeName;
//    @Column(name = "designation")
//    private String designation;
//    @Column(name = "date_of_joining")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private LocalDate dateOfJoining;
//    @Column(name = "gender")
//    private String gender;
//    @Column(name = "email_id")
//    private String emailId;
//    @Column(name = "work_location")
//    private String workLocation;
//    @Column(name = "department")
//    private String department;
//    @Column(name = "document_id")
//    private String documentId;
//    @Column(name = "generate_on")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date generateOn;
//    @Column(name = "generate_by")
//    private String generateBy;
//    @Column(name = "sent_on")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date sentOn;
//    @Column(name = "sent_by")
//    private String sentBy;
//    @Column(name = "status")
//    private String status;
//    @Lob
//    @Column(name = "url")
//    private String url;
//    @Column(name = "offered_salaray")
//    private String offeredSalaray;
//     @Column(name = "applicant_id")
//    private Integer applicantId;
//
//    public Loi() {
//    }
//
//    public Loi(Long id) {
//        this.id = id;
//    }
//
//    public Integer getApplicantId() {
//        return applicantId;
//    }
//
//    public void setApplicantId(Integer applicantId) {
//        this.applicantId = applicantId;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getEmployeeName() {
//        return employeeName;
//    }
//
//    public void setEmployeeName(String employeeName) {
//        this.employeeName = employeeName;
//    }
//
//    public String getDesignation() {
//        return designation;
//    }
//
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
//
//    public LocalDate getDateOfJoining() {
//        return dateOfJoining;
//    }
//
//    public void setDateOfJoining(LocalDate dateOfJoining) {
//        this.dateOfJoining = dateOfJoining;
//    }
//
//   
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getEmailId() {
//        return emailId;
//    }
//
//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
//
//    public String getWorkLocation() {
//        return workLocation;
//    }
//
//    public void setWorkLocation(String workLocation) {
//        this.workLocation = workLocation;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
//
//    public String getDocumentId() {
//        return documentId;
//    }
//
//    public void setDocumentId(String documentId) {
//        this.documentId = documentId;
//    }
//
//    public Date getGenerateOn() {
//        return generateOn;
//    }
//
//    public void setGenerateOn(Date generateOn) {
//        this.generateOn = generateOn;
//    }
//
//    public String getGenerateBy() {
//        return generateBy;
//    }
//
//    public void setGenerateBy(String generateBy) {
//        this.generateBy = generateBy;
//    }
//
//    public Date getSentOn() {
//        return sentOn;
//    }
//
//    public void setSentOn(Date sentOn) {
//        this.sentOn = sentOn;
//    }
//
//    public String getSentBy() {
//        return sentBy;
//    }
//
//    public void setSentBy(String sentBy) {
//        this.sentBy = sentBy;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getOfferedSalaray() {
//        return offeredSalaray;
//    }
//
//    public void setOfferedSalaray(String offeredSalaray) {
//        this.offeredSalaray = offeredSalaray;
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
//        if (!(object instanceof Loi)) {
//            return false;
//        }
//        Loi other = (Loi) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.tcInterviewform.TCInterviewForm.model.LetterIssue.Loi[ id=" + id + " ]";
//    }
//    
//}
