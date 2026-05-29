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
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.format.annotation.DateTimeFormat;
//
///**
// *
// * @author teamd
// */
//@Entity
//@Table(name = "old_employee_details")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "OldEmployeeDetails.findAll", query = "SELECT o FROM OldEmployeeDetails o")
//    , @NamedQuery(name = "OldEmployeeDetails.findById", query = "SELECT o FROM OldEmployeeDetails o WHERE o.id = :id")
//    , @NamedQuery(name = "OldEmployeeDetails.findByEmployeeId", query = "SELECT o FROM OldEmployeeDetails o WHERE o.employeeId = :employeeId")
//    , @NamedQuery(name = "OldEmployeeDetails.findByEmployeeName", query = "SELECT o FROM OldEmployeeDetails o WHERE o.employeeName = :employeeName")
//    , @NamedQuery(name = "OldEmployeeDetails.findByGender", query = "SELECT o FROM OldEmployeeDetails o WHERE o.gender = :gender")
//    , @NamedQuery(name = "OldEmployeeDetails.findByDateOfBirth", query = "SELECT o FROM OldEmployeeDetails o WHERE o.dateOfBirth = :dateOfBirth")
//    , @NamedQuery(name = "OldEmployeeDetails.findByDateOfJoining", query = "SELECT o FROM OldEmployeeDetails o WHERE o.dateOfJoining = :dateOfJoining")
//    , @NamedQuery(name = "OldEmployeeDetails.findByLocation", query = "SELECT o FROM OldEmployeeDetails o WHERE o.location = :location")
//    , @NamedQuery(name = "OldEmployeeDetails.findByLevels", query = "SELECT o FROM OldEmployeeDetails o WHERE o.levels = :levels")
//    , @NamedQuery(name = "OldEmployeeDetails.findByDesignation", query = "SELECT o FROM OldEmployeeDetails o WHERE o.designation = :designation")
//    , @NamedQuery(name = "OldEmployeeDetails.findByEducation", query = "SELECT o FROM OldEmployeeDetails o WHERE o.education = :education")
//    , @NamedQuery(name = "OldEmployeeDetails.findByEmailId", query = "SELECT o FROM OldEmployeeDetails o WHERE o.emailId = :emailId")
//    , @NamedQuery(name = "OldEmployeeDetails.findByCreatedAt", query = "SELECT o FROM OldEmployeeDetails o WHERE o.createdAt = :createdAt")
//    , @NamedQuery(name = "OldEmployeeDetails.findByUpdatedAt", query = "SELECT o FROM OldEmployeeDetails o WHERE o.updatedAt = :updatedAt")
//    , @NamedQuery(name = "OldEmployeeDetails.findByContactNumber", query = "SELECT o FROM OldEmployeeDetails o WHERE o.contactNumber = :contactNumber")
//    , @NamedQuery(name = "OldEmployeeDetails.findByOfferLetterStatus", query = "SELECT o FROM OldEmployeeDetails o WHERE o.offerLetterStatus = :offerLetterStatus")
//    , @NamedQuery(name = "OldEmployeeDetails.findBySalBifurcationId", query = "SELECT o FROM OldEmployeeDetails o WHERE o.salBifurcationId = :salBifurcationId")
//    , @NamedQuery(name = "OldEmployeeDetails.findByCity", query = "SELECT o FROM OldEmployeeDetails o WHERE o.city = :city")
//    , @NamedQuery(name = "OldEmployeeDetails.findByState", query = "SELECT o FROM OldEmployeeDetails o WHERE o.state = :state")
//    , @NamedQuery(name = "OldEmployeeDetails.findByPincode", query = "SELECT o FROM OldEmployeeDetails o WHERE o.pincode = :pincode")
//    , @NamedQuery(name = "OldEmployeeDetails.findByCtc", query = "SELECT o FROM OldEmployeeDetails o WHERE o.ctc = :ctc")
//    , @NamedQuery(name = "OldEmployeeDetails.findByBasic", query = "SELECT o FROM OldEmployeeDetails o WHERE o.basic = :basic")
//    , @NamedQuery(name = "OldEmployeeDetails.findByHra", query = "SELECT o FROM OldEmployeeDetails o WHERE o.hra = :hra")
//    , @NamedQuery(name = "OldEmployeeDetails.findByConveyance", query = "SELECT o FROM OldEmployeeDetails o WHERE o.conveyance = :conveyance")
//    , @NamedQuery(name = "OldEmployeeDetails.findByMedical", query = "SELECT o FROM OldEmployeeDetails o WHERE o.medical = :medical")
//    , @NamedQuery(name = "OldEmployeeDetails.findBySpecialAllowance", query = "SELECT o FROM OldEmployeeDetails o WHERE o.specialAllowance = :specialAllowance")
//    , @NamedQuery(name = "OldEmployeeDetails.findByGrossSalary", query = "SELECT o FROM OldEmployeeDetails o WHERE o.grossSalary = :grossSalary")
//    , @NamedQuery(name = "OldEmployeeDetails.findByEmployeePf", query = "SELECT o FROM OldEmployeeDetails o WHERE o.employeePf = :employeePf")
//    , @NamedQuery(name = "OldEmployeeDetails.findByEmployerPf", query = "SELECT o FROM OldEmployeeDetails o WHERE o.employerPf = :employerPf")
//    , @NamedQuery(name = "OldEmployeeDetails.findByProfessionalTax", query = "SELECT o FROM OldEmployeeDetails o WHERE o.professionalTax = :professionalTax")
//    , @NamedQuery(name = "OldEmployeeDetails.findByInsuranceDeduction", query = "SELECT o FROM OldEmployeeDetails o WHERE o.insuranceDeduction = :insuranceDeduction")
//    , @NamedQuery(name = "OldEmployeeDetails.findByTotalDeduction", query = "SELECT o FROM OldEmployeeDetails o WHERE o.totalDeduction = :totalDeduction")
//    , @NamedQuery(name = "OldEmployeeDetails.findByNetSalary", query = "SELECT o FROM OldEmployeeDetails o WHERE o.netSalary = :netSalary")})
//public class OldEmployeeDetails implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Long id;
//    @Basic(optional = false)
//    @Column(name = "employee_id")
//    private String employeeId;
//    @Basic(optional = false)
//    @Column(name = "employee_name")
//    private String employeeName;
//    @Column(name = "gender")
//    private String gender;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_birth")
//    private LocalDate dateOfBirth;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @Column(name = "date_of_joining")
//    private LocalDate dateOfJoining;
//    @Column(name = "location")
//    private String location;
//    @Column(name = "levels")
//    private String levels;
//    @Column(name = "designation")
//    private String designation;
//    @Column(name = "education")
//    private String education;
//    @Column(name = "email_id")
//    private String emailId;
//    @Lob
//    @Column(name = "employee_address")
//    private String employeeAddress;
//    @CreationTimestamp
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//    @Column(name = "contact_number")
//    private String contactNumber;
//    @Column(name = "offer_letter_status")
//    private String offerLetterStatus;
//    @Column(name = "sal_bifurcation_id")
//    private String salBifurcationId;
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
//
//    public OldEmployeeDetails() {
//    }
//
//    public OldEmployeeDetails(Long id) {
//        this.id = id;
//    }
//
//    public OldEmployeeDetails(Long id, String employeeId, String employeeName) {
//        this.id = id;
//        this.employeeId = employeeId;
//        this.employeeName = employeeName;
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
//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        this.employeeId = employeeId;
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
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
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
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getLevels() {
//        return levels;
//    }
//
//    public void setLevels(String levels) {
//        this.levels = levels;
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
//    public String getEducation() {
//        return education;
//    }
//
//    public void setEducation(String education) {
//        this.education = education;
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
//    public String getContactNumber() {
//        return contactNumber;
//    }
//
//    public void setContactNumber(String contactNumber) {
//        this.contactNumber = contactNumber;
//    }
//
//    public String getOfferLetterStatus() {
//        return offerLetterStatus;
//    }
//
//    public void setOfferLetterStatus(String offerLetterStatus) {
//        this.offerLetterStatus = offerLetterStatus;
//    }
//
//    public String getSalBifurcationId() {
//        return salBifurcationId;
//    }
//
//    public void setSalBifurcationId(String salBifurcationId) {
//        this.salBifurcationId = salBifurcationId;
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
//        if (!(object instanceof OldEmployeeDetails)) {
//            return false;
//        }
//        OldEmployeeDetails other = (OldEmployeeDetails) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.tcInterviewform.TCInterviewForm.model.LetterIssue.OldEmployeeDetails[ id=" + id + " ]";
//    }
//    
//}
