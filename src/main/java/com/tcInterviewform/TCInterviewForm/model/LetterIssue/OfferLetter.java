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
//@Table(name = "offer_letter")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "OfferLetter.findAll", query = "SELECT o FROM OfferLetter o")
//    , @NamedQuery(name = "OfferLetter.findById", query = "SELECT o FROM OfferLetter o WHERE o.id = :id")
//    , @NamedQuery(name = "OfferLetter.findByEmployeeName", query = "SELECT o FROM OfferLetter o WHERE o.employeeName = :employeeName")
//    , @NamedQuery(name = "OfferLetter.findByGender", query = "SELECT o FROM OfferLetter o WHERE o.gender = :gender")
//    , @NamedQuery(name = "OfferLetter.findByDateOfJoining", query = "SELECT o FROM OfferLetter o WHERE o.dateOfJoining = :dateOfJoining")
//    , @NamedQuery(name = "OfferLetter.findByWorkLocation", query = "SELECT o FROM OfferLetter o WHERE o.workLocation = :workLocation")
//    , @NamedQuery(name = "OfferLetter.findByDesignation", query = "SELECT o FROM OfferLetter o WHERE o.designation = :designation")
//    , @NamedQuery(name = "OfferLetter.findByEmailId", query = "SELECT o FROM OfferLetter o WHERE o.emailId = :emailId")
//    , @NamedQuery(name = "OfferLetter.findByCreatedAt", query = "SELECT o FROM OfferLetter o WHERE o.createdAt = :createdAt")
//    , @NamedQuery(name = "OfferLetter.findByUpdatedAt", query = "SELECT o FROM OfferLetter o WHERE o.updatedAt = :updatedAt")
//    , @NamedQuery(name = "OfferLetter.findByCity", query = "SELECT o FROM OfferLetter o WHERE o.city = :city")
//    , @NamedQuery(name = "OfferLetter.findByState", query = "SELECT o FROM OfferLetter o WHERE o.state = :state")
//    , @NamedQuery(name = "OfferLetter.findByPincode", query = "SELECT o FROM OfferLetter o WHERE o.pincode = :pincode")
//    , @NamedQuery(name = "OfferLetter.findByCtc", query = "SELECT o FROM OfferLetter o WHERE o.ctc = :ctc")
//    , @NamedQuery(name = "OfferLetter.findByBasic", query = "SELECT o FROM OfferLetter o WHERE o.basic = :basic")
//    , @NamedQuery(name = "OfferLetter.findByHra", query = "SELECT o FROM OfferLetter o WHERE o.hra = :hra")
//    , @NamedQuery(name = "OfferLetter.findByConveyance", query = "SELECT o FROM OfferLetter o WHERE o.conveyance = :conveyance")
//    , @NamedQuery(name = "OfferLetter.findByMedical", query = "SELECT o FROM OfferLetter o WHERE o.medical = :medical")
//    , @NamedQuery(name = "OfferLetter.findBySpecialAllowance", query = "SELECT o FROM OfferLetter o WHERE o.specialAllowance = :specialAllowance")
//    , @NamedQuery(name = "OfferLetter.findByGrossSalary", query = "SELECT o FROM OfferLetter o WHERE o.grossSalary = :grossSalary")
//    , @NamedQuery(name = "OfferLetter.findByEmployeePf", query = "SELECT o FROM OfferLetter o WHERE o.employeePf = :employeePf")
//    , @NamedQuery(name = "OfferLetter.findByEmployerPf", query = "SELECT o FROM OfferLetter o WHERE o.employerPf = :employerPf")
//    , @NamedQuery(name = "OfferLetter.findByProfessionalTax", query = "SELECT o FROM OfferLetter o WHERE o.professionalTax = :professionalTax")
//    , @NamedQuery(name = "OfferLetter.findByInsuranceDeduction", query = "SELECT o FROM OfferLetter o WHERE o.insuranceDeduction = :insuranceDeduction")
//    , @NamedQuery(name = "OfferLetter.findByTotalDeduction", query = "SELECT o FROM OfferLetter o WHERE o.totalDeduction = :totalDeduction")
//    , @NamedQuery(name = "OfferLetter.findByNetSalary", query = "SELECT o FROM OfferLetter o WHERE o.netSalary = :netSalary")
//    , @NamedQuery(name = "OfferLetter.findByDocumentId", query = "SELECT o FROM OfferLetter o WHERE o.documentId = :documentId")
//    , @NamedQuery(name = "OfferLetter.findByGenerateOn", query = "SELECT o FROM OfferLetter o WHERE o.generateOn = :generateOn")
//    , @NamedQuery(name = "OfferLetter.findByGenerateBy", query = "SELECT o FROM OfferLetter o WHERE o.generateBy = :generateBy")
//    , @NamedQuery(name = "OfferLetter.findBySentOn", query = "SELECT o FROM OfferLetter o WHERE o.sentOn = :sentOn")
//    , @NamedQuery(name = "OfferLetter.findBySentBy", query = "SELECT o FROM OfferLetter o WHERE o.sentBy = :sentBy")
//    , @NamedQuery(name = "OfferLetter.findByStatus", query = "SELECT o FROM OfferLetter o WHERE o.status = :status")})
//public class OfferLetter implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Long id;
//    @Basic(optional = false)
//    @Column(name = "employee_name")
//    private String employeeName;
//    @Column(name = "gender")
//    private String gender;
//     @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_joining")
//    private LocalDate dateOfJoining;
//    @Column(name = "work_location")
//    private String workLocation;
//    @Column(name = "designation")
//    private String designation;
//    @Column(name = "email_id")
//    private String emailId;
//    @Lob
//    @Column(name = "employee_address")
//    private String employeeAddress;
//    @Basic(optional = false)
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//    @Column(name = "city")
//    private String city;
//    @Column(name = "state")
//    private String state;
//    @Column(name = "pincode")
//    private String pincode;
//    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
//    @Column(name = "ctc")
//    private Double ctc;
//    @Column(name = "basic")
//    private Double basic;
//    @Column(name = "hra")
//    private Double hra;
//    @Column(name = "conveyance")
//    private Double conveyance;
//    @Column(name = "medical")
//    private Double medical;
//    @Column(name = "special_allowance")
//    private Double specialAllowance;
//    @Column(name = "gross_salary")
//    private Double grossSalary;
//    @Column(name = "employee_pf")
//    private Double employeePf;
//    @Column(name = "employer_pf")
//    private Double employerPf;
//    @Column(name = "professional_tax")
//    private Double professionalTax;
//    @Column(name = "insurance_deduction")
//    private Double insuranceDeduction;
//    @Column(name = "total_deduction")
//    private Double totalDeduction;
//    @Column(name = "net_salary")
//    private Double netSalary;
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
//    @Column(name = "url")
//    private String url;
// @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_birth")
//    private LocalDate dateOfBirth;
//    public OfferLetter() {
//    }
//
//    public OfferLetter(Long id) {
//        this.id = id;
//    }
//
//    public OfferLetter(Long id, String employeeName, Date createdAt) {
//        this.id = id;
//        this.employeeName = employeeName;
//        this.createdAt = createdAt;
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
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
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
//    public String getWorkLocation() {
//        return workLocation;
//    }
//
//    public void setWorkLocation(String workLocation) {
//        this.workLocation = workLocation;
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
//    public String getEmailId() {
//        return emailId;
//    }
//
//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
//
//    public String getEmployeeAddress() {
//        return employeeAddress;
//    }
//
//    public void setEmployeeAddress(String employeeAddress) {
//        this.employeeAddress = employeeAddress;
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
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getPincode() {
//        return pincode;
//    }
//
//    public void setPincode(String pincode) {
//        this.pincode = pincode;
//    }
//
//    public Double getCtc() {
//        return ctc;
//    }
//
//    public void setCtc(Double ctc) {
//        this.ctc = ctc;
//    }
//
//    public Double getBasic() {
//        return basic;
//    }
//
//    public void setBasic(Double basic) {
//        this.basic = basic;
//    }
//
//    public Double getHra() {
//        return hra;
//    }
//
//    public void setHra(Double hra) {
//        this.hra = hra;
//    }
//
//    public Double getConveyance() {
//        return conveyance;
//    }
//
//    public void setConveyance(Double conveyance) {
//        this.conveyance = conveyance;
//    }
//
//    public Double getMedical() {
//        return medical;
//    }
//
//    public void setMedical(Double medical) {
//        this.medical = medical;
//    }
//
//    public Double getSpecialAllowance() {
//        return specialAllowance;
//    }
//
//    public void setSpecialAllowance(Double specialAllowance) {
//        this.specialAllowance = specialAllowance;
//    }
//
//    public Double getGrossSalary() {
//        return grossSalary;
//    }
//
//    public void setGrossSalary(Double grossSalary) {
//        this.grossSalary = grossSalary;
//    }
//
//    public Double getEmployeePf() {
//        return employeePf;
//    }
//
//    public void setEmployeePf(Double employeePf) {
//        this.employeePf = employeePf;
//    }
//
//    public Double getEmployerPf() {
//        return employerPf;
//    }
//
//    public void setEmployerPf(Double employerPf) {
//        this.employerPf = employerPf;
//    }
//
//    public Double getProfessionalTax() {
//        return professionalTax;
//    }
//
//    public void setProfessionalTax(Double professionalTax) {
//        this.professionalTax = professionalTax;
//    }
//
//    public Double getInsuranceDeduction() {
//        return insuranceDeduction;
//    }
//
//    public void setInsuranceDeduction(Double insuranceDeduction) {
//        this.insuranceDeduction = insuranceDeduction;
//    }
//
//    public Double getTotalDeduction() {
//        return totalDeduction;
//    }
//
//    public void setTotalDeduction(Double totalDeduction) {
//        this.totalDeduction = totalDeduction;
//    }
//
//    public Double getNetSalary() {
//        return netSalary;
//    }
//
//    public void setNetSalary(Double netSalary) {
//        this.netSalary = netSalary;
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
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
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
//        if (!(object instanceof OfferLetter)) {
//            return false;
//        }
//        OfferLetter other = (OfferLetter) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.tcInterviewform.TCInterviewForm.repository.LetterIssue.OfferLetter[ id=" + id + " ]";
//    }
//    
//}
