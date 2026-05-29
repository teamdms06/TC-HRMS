//
//package com.tcInterviewform.TCInterviewForm.model.LetterIssue;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "increment_promotion")
//public class IncrementPromotion {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String employeeName;
//    private String employeeId;
//    private String department;
//    private String designation;
//    private String grade;
//
//    private LocalDate dateOfJoining;
//
//    private String promotedDesignation;
//    private String upgradedLevel;
//
//    private BigDecimal currentGrossSalary;
//    private BigDecimal revisedGrossSalary;
//
//    private LocalDate incrementDate;
//
//    private String type;
//    private String documentId;
//
//    private LocalDateTime generatedOn;
//    private String generatedBy;
//
//    private LocalDateTime sentOn;
//    private String sentBy;
//
//    private String status;
//    private String url;
//    private String emailId;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    public void onCreate() {
//        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
//
//        if (status == null || status.trim().isEmpty()) {
//            status = "Pending";
//        }
//    }
//
//    @PreUpdate
//    public void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//
//    public Long getId() {
//        return id;
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
//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        this.employeeId = employeeId;
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
//    public String getDesignation() {
//        return designation;
//    }
//
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
//
//    public String getGrade() {
//        return grade;
//    }
//
//    public void setGrade(String grade) {
//        this.grade = grade;
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
//    public String getPromotedDesignation() {
//        return promotedDesignation;
//    }
//
//    public void setPromotedDesignation(String promotedDesignation) {
//        this.promotedDesignation = promotedDesignation;
//    }
//
//    public String getUpgradedLevel() {
//        return upgradedLevel;
//    }
//
//    public void setUpgradedLevel(String upgradedLevel) {
//        this.upgradedLevel = upgradedLevel;
//    }
//
//    public BigDecimal getCurrentGrossSalary() {
//        return currentGrossSalary;
//    }
//
//    public void setCurrentGrossSalary(BigDecimal currentGrossSalary) {
//        this.currentGrossSalary = currentGrossSalary;
//    }
//
//    public BigDecimal getRevisedGrossSalary() {
//        return revisedGrossSalary;
//    }
//
//    public void setRevisedGrossSalary(BigDecimal revisedGrossSalary) {
//        this.revisedGrossSalary = revisedGrossSalary;
//    }
//
//    public LocalDate getIncrementDate() {
//        return incrementDate;
//    }
//
//    public void setIncrementDate(LocalDate incrementDate) {
//        this.incrementDate = incrementDate;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
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
//    public LocalDateTime getGeneratedOn() {
//        return generatedOn;
//    }
//
//    public void setGeneratedOn(LocalDateTime generatedOn) {
//        this.generatedOn = generatedOn;
//    }
//
//    public String getGeneratedBy() {
//        return generatedBy;
//    }
//
//    public void setGeneratedBy(String generatedBy) {
//        this.generatedBy = generatedBy;
//    }
//
//    public LocalDateTime getSentOn() {
//        return sentOn;
//    }
//
//    public void setSentOn(LocalDateTime sentOn) {
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
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(LocalDateTime createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//
//    
//}
