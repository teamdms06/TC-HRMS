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
@Table(name = "emp_family_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpFamilyDetails.findAll", query = "SELECT e FROM EmpFamilyDetails e")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyId", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyId = :familyId")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyCreatedAt", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyCreatedAt = :familyCreatedAt")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyEmpId", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyEmpId = :familyEmpId")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyFullName", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyFullName = :familyFullName")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyOccupation", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyOccupation = :familyOccupation")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyPhone", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyPhone = :familyPhone")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyRealation", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyRealation = :familyRealation")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyStatus", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyStatus = :familyStatus")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyUpdatedAt", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyUpdatedAt = :familyUpdatedAt")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyUserId", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyUserId = :familyUserId")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyUserName", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyUserName = :familyUserName")
    , @NamedQuery(name = "EmpFamilyDetails.findByFamilyJobappId", query = "SELECT e FROM EmpFamilyDetails e WHERE e.familyJobappId = :familyJobappId")})
public class EmpFamilyDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "family_id")
    private Integer familyId;
    @CreationTimestamp
    @Column(name = "family_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date familyCreatedAt;
    @Column(name = "family_emp_id")
    private Integer familyEmpId;
    @Column(name = "family_full_name")
    private String familyFullName;
    @Column(name = "family_occupation")
    private String familyOccupation;
    @Column(name = "family_phone")
    private String familyPhone;
    @Column(name = "family_realation")
    private String familyRealation;
    @Column(name = "family_status")
    private Short familyStatus;
    @UpdateTimestamp
    @Column(name = "family_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date familyUpdatedAt;
    @Column(name = "family_user_id")
    private Integer familyUserId;
    @Column(name = "family_user_name")
    private String familyUserName;
    @Column(name = "family_jobapp_id")
    private Integer familyJobappId;

    public EmpFamilyDetails() {
    }

    public EmpFamilyDetails(Integer familyId) {
        this.familyId = familyId;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public Date getFamilyCreatedAt() {
        return familyCreatedAt;
    }

    public void setFamilyCreatedAt(Date familyCreatedAt) {
        this.familyCreatedAt = familyCreatedAt;
    }

    public Integer getFamilyEmpId() {
        return familyEmpId;
    }

    public void setFamilyEmpId(Integer familyEmpId) {
        this.familyEmpId = familyEmpId;
    }

    public String getFamilyFullName() {
        return familyFullName;
    }

    public void setFamilyFullName(String familyFullName) {
        this.familyFullName = familyFullName;
    }

    public String getFamilyOccupation() {
        return familyOccupation;
    }

    public void setFamilyOccupation(String familyOccupation) {
        this.familyOccupation = familyOccupation;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getFamilyRealation() {
        return familyRealation;
    }

    public void setFamilyRealation(String familyRealation) {
        this.familyRealation = familyRealation;
    }

    public Short getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(Short familyStatus) {
        this.familyStatus = familyStatus;
    }

    public Date getFamilyUpdatedAt() {
        return familyUpdatedAt;
    }

    public void setFamilyUpdatedAt(Date familyUpdatedAt) {
        this.familyUpdatedAt = familyUpdatedAt;
    }

    public Integer getFamilyUserId() {
        return familyUserId;
    }

    public void setFamilyUserId(Integer familyUserId) {
        this.familyUserId = familyUserId;
    }

    public String getFamilyUserName() {
        return familyUserName;
    }

    public void setFamilyUserName(String familyUserName) {
        this.familyUserName = familyUserName;
    }

    public Integer getFamilyJobappId() {
        return familyJobappId;
    }

    public void setFamilyJobappId(Integer familyJobappId) {
        this.familyJobappId = familyJobappId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (familyId != null ? familyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpFamilyDetails)) {
            return false;
        }
        EmpFamilyDetails other = (EmpFamilyDetails) object;
        if ((this.familyId == null && other.familyId != null) || (this.familyId != null && !this.familyId.equals(other.familyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.EmpFamilyDetails[ familyId=" + familyId + " ]";
    }
    
}
