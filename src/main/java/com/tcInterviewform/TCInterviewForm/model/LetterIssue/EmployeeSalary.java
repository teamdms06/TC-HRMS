/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model.LetterIssue;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author teamd
 */
@Entity
@Table(name = "employee_salary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmployeeSalary.findAll", query = "SELECT e FROM EmployeeSalary e")
    , @NamedQuery(name = "EmployeeSalary.findById", query = "SELECT e FROM EmployeeSalary e WHERE e.id = :id")
    , @NamedQuery(name = "EmployeeSalary.findByApplicantId", query = "SELECT e FROM EmployeeSalary e WHERE e.applicantId = :applicantId")
    , @NamedQuery(name = "EmployeeSalary.findByCtc", query = "SELECT e FROM EmployeeSalary e WHERE e.ctc = :ctc")
    , @NamedQuery(name = "EmployeeSalary.findByBasic", query = "SELECT e FROM EmployeeSalary e WHERE e.basic = :basic")
    , @NamedQuery(name = "EmployeeSalary.findByHra", query = "SELECT e FROM EmployeeSalary e WHERE e.hra = :hra")
    , @NamedQuery(name = "EmployeeSalary.findByConveyance", query = "SELECT e FROM EmployeeSalary e WHERE e.conveyance = :conveyance")
    , @NamedQuery(name = "EmployeeSalary.findByMedical", query = "SELECT e FROM EmployeeSalary e WHERE e.medical = :medical")
    , @NamedQuery(name = "EmployeeSalary.findBySpecialAllowance", query = "SELECT e FROM EmployeeSalary e WHERE e.specialAllowance = :specialAllowance")
    , @NamedQuery(name = "EmployeeSalary.findByGrossSalary", query = "SELECT e FROM EmployeeSalary e WHERE e.grossSalary = :grossSalary")
    , @NamedQuery(name = "EmployeeSalary.findByEmployeePf", query = "SELECT e FROM EmployeeSalary e WHERE e.employeePf = :employeePf")
    , @NamedQuery(name = "EmployeeSalary.findByProfessionalTax", query = "SELECT e FROM EmployeeSalary e WHERE e.professionalTax = :professionalTax")
    , @NamedQuery(name = "EmployeeSalary.findByInsuranceDeduction", query = "SELECT e FROM EmployeeSalary e WHERE e.insuranceDeduction = :insuranceDeduction")
    , @NamedQuery(name = "EmployeeSalary.findByTotalDeduction", query = "SELECT e FROM EmployeeSalary e WHERE e.totalDeduction = :totalDeduction")
    , @NamedQuery(name = "EmployeeSalary.findByNetSalary", query = "SELECT e FROM EmployeeSalary e WHERE e.netSalary = :netSalary")
    , @NamedQuery(name = "EmployeeSalary.findByCreatedDate", query = "SELECT e FROM EmployeeSalary e WHERE e.createdDate = :createdDate")})
public class EmployeeSalary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "applicant_id")
    private Integer applicantId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ctc")
    private Double ctc;
    @Column(name = "basic")
    private Double basic;
    @Column(name = "hra")
    private Double hra;
    @Column(name = "conveyance")
    private Double conveyance;
    @Column(name = "medical")
    private Double medical;
    @Column(name = "special_allowance")
    private Double specialAllowance;
    @Column(name = "gross_salary")
    private Double grossSalary;
    @Column(name = "employee_pf")
    private Double employeePf;
    @Column(name = "professional_tax")
    private Double professionalTax;
    @Column(name = "insurance_deduction")
    private Double insuranceDeduction;
    @Column(name = "total_deduction")
    private Double totalDeduction;
    @Column(name = "net_salary")
    private Double netSalary;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_date")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "tc_id")
    private String tcId;

    public EmployeeSalary() {
    }

    public EmployeeSalary(Long id) {
        this.id = id;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    public Double getCtc() {
        return ctc;
    }

    public void setCtc(Double ctc) {
        this.ctc = ctc;
    }

    public Double getBasic() {
        return basic;
    }

    public void setBasic(Double basic) {
        this.basic = basic;
    }

    public Double getHra() {
        return hra;
    }

    public void setHra(Double hra) {
        this.hra = hra;
    }

    public Double getConveyance() {
        return conveyance;
    }

    public void setConveyance(Double conveyance) {
        this.conveyance = conveyance;
    }

    public Double getMedical() {
        return medical;
    }

    public void setMedical(Double medical) {
        this.medical = medical;
    }

    public Double getSpecialAllowance() {
        return specialAllowance;
    }

    public void setSpecialAllowance(Double specialAllowance) {
        this.specialAllowance = specialAllowance;
    }

    public Double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(Double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public Double getEmployeePf() {
        return employeePf;
    }

    public void setEmployeePf(Double employeePf) {
        this.employeePf = employeePf;
    }

    public Double getProfessionalTax() {
        return professionalTax;
    }

    public void setProfessionalTax(Double professionalTax) {
        this.professionalTax = professionalTax;
    }

    public Double getInsuranceDeduction() {
        return insuranceDeduction;
    }

    public void setInsuranceDeduction(Double insuranceDeduction) {
        this.insuranceDeduction = insuranceDeduction;
    }

    public Double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(Double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public Double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(Double netSalary) {
        this.netSalary = netSalary;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeSalary)) {
            return false;
        }
        EmployeeSalary other = (EmployeeSalary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.LetterIssue.EmployeeSalary[ id=" + id + " ]";
    }
    
}
