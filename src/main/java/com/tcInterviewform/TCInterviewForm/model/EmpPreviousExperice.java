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
@Table(name = "emp_previous_experice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpPreviousExperice.findAll", query = "SELECT e FROM EmpPreviousExperice e")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpId", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expId = :expId")
    , @NamedQuery(name = "EmpPreviousExperice.findByEmpUserId", query = "SELECT e FROM EmpPreviousExperice e WHERE e.empUserId = :empUserId")
    , @NamedQuery(name = "EmpPreviousExperice.findByEmpUserName", query = "SELECT e FROM EmpPreviousExperice e WHERE e.empUserName = :empUserName")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpCreatedAt", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expCreatedAt = :expCreatedAt")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpDesignation", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expDesignation = :expDesignation")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpDuration", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expDuration = :expDuration")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpEmpId", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expEmpId = :expEmpId")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpPreviousCompanyName", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expPreviousCompanyName = :expPreviousCompanyName")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpReasonToLeaveCompany", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expReasonToLeaveCompany = :expReasonToLeaveCompany")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpSalaryOffered", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expSalaryOffered = :expSalaryOffered")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpStatus", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expStatus = :expStatus")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpUpdatedAt", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expUpdatedAt = :expUpdatedAt")
    , @NamedQuery(name = "EmpPreviousExperice.findByExpJobappId", query = "SELECT e FROM EmpPreviousExperice e WHERE e.expJobappId = :expJobappId")})
public class EmpPreviousExperice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exp_id")
    private Integer expId;
    @Column(name = "emp_user_id")
    private Integer empUserId;
    @Column(name = "emp_user_name")
    private String empUserName;
    @CreationTimestamp
    @Column(name = "exp_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expCreatedAt;
    @Column(name = "exp_designation")
    private String expDesignation;
    @Column(name = "exp_duration")
    private String expDuration;
    @Column(name = "exp_emp_id")
    private Integer expEmpId;
    @Column(name = "exp_previous_company_name")
    private String expPreviousCompanyName;
    @Column(name = "exp_reason_to_leave_company")
    private String expReasonToLeaveCompany;
    @Column(name = "exp_salary_offered")
    private String expSalaryOffered;
    @Column(name = "exp_status")
    private Short expStatus;
    @UpdateTimestamp
    @Column(name = "exp_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expUpdatedAt;
    @Column(name = "exp_jobapp_id")
    private Integer expJobappId;

    public EmpPreviousExperice() {
    }

    public EmpPreviousExperice(Integer expId) {
        this.expId = expId;
    }

    public Integer getExpId() {
        return expId;
    }

    public void setExpId(Integer expId) {
        this.expId = expId;
    }

    public Integer getEmpUserId() {
        return empUserId;
    }

    public void setEmpUserId(Integer empUserId) {
        this.empUserId = empUserId;
    }

    public String getEmpUserName() {
        return empUserName;
    }

    public void setEmpUserName(String empUserName) {
        this.empUserName = empUserName;
    }

    public Date getExpCreatedAt() {
        return expCreatedAt;
    }

    public void setExpCreatedAt(Date expCreatedAt) {
        this.expCreatedAt = expCreatedAt;
    }

    public String getExpDesignation() {
        return expDesignation;
    }

    public void setExpDesignation(String expDesignation) {
        this.expDesignation = expDesignation;
    }

    public String getExpDuration() {
        return expDuration;
    }

    public void setExpDuration(String expDuration) {
        this.expDuration = expDuration;
    }

    public Integer getExpEmpId() {
        return expEmpId;
    }

    public void setExpEmpId(Integer expEmpId) {
        this.expEmpId = expEmpId;
    }

    public String getExpPreviousCompanyName() {
        return expPreviousCompanyName;
    }

    public void setExpPreviousCompanyName(String expPreviousCompanyName) {
        this.expPreviousCompanyName = expPreviousCompanyName;
    }

    public String getExpReasonToLeaveCompany() {
        return expReasonToLeaveCompany;
    }

    public void setExpReasonToLeaveCompany(String expReasonToLeaveCompany) {
        this.expReasonToLeaveCompany = expReasonToLeaveCompany;
    }

    public String getExpSalaryOffered() {
        return expSalaryOffered;
    }

    public void setExpSalaryOffered(String expSalaryOffered) {
        this.expSalaryOffered = expSalaryOffered;
    }

    public Short getExpStatus() {
        return expStatus;
    }

    public void setExpStatus(Short expStatus) {
        this.expStatus = expStatus;
    }

    public Date getExpUpdatedAt() {
        return expUpdatedAt;
    }

    public void setExpUpdatedAt(Date expUpdatedAt) {
        this.expUpdatedAt = expUpdatedAt;
    }

    public Integer getExpJobappId() {
        return expJobappId;
    }

    public void setExpJobappId(Integer expJobappId) {
        this.expJobappId = expJobappId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expId != null ? expId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpPreviousExperice)) {
            return false;
        }
        EmpPreviousExperice other = (EmpPreviousExperice) object;
        if ((this.expId == null && other.expId != null) || (this.expId != null && !this.expId.equals(other.expId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.EmpPreviousExperice[ expId=" + expId + " ]";
    }
    
}
