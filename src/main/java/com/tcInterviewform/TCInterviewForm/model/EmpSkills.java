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
@Table(name = "emp_skills")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpSkills.findAll", query = "SELECT e FROM EmpSkills e")
    , @NamedQuery(name = "EmpSkills.findBySkillId", query = "SELECT e FROM EmpSkills e WHERE e.skillId = :skillId")
    , @NamedQuery(name = "EmpSkills.findBySkillCollegeUniInstituteName", query = "SELECT e FROM EmpSkills e WHERE e.skillCollegeUniInstituteName = :skillCollegeUniInstituteName")
    , @NamedQuery(name = "EmpSkills.findBySkillCreatedAt", query = "SELECT e FROM EmpSkills e WHERE e.skillCreatedAt = :skillCreatedAt")
    , @NamedQuery(name = "EmpSkills.findBySkillDivisionPercentageOfMarks", query = "SELECT e FROM EmpSkills e WHERE e.skillDivisionPercentageOfMarks = :skillDivisionPercentageOfMarks")
    , @NamedQuery(name = "EmpSkills.findBySkillEmpId", query = "SELECT e FROM EmpSkills e WHERE e.skillEmpId = :skillEmpId")
    , @NamedQuery(name = "EmpSkills.findBySkillExamination", query = "SELECT e FROM EmpSkills e WHERE e.skillExamination = :skillExamination")
    , @NamedQuery(name = "EmpSkills.findBySkillQualification", query = "SELECT e FROM EmpSkills e WHERE e.skillQualification = :skillQualification")
    , @NamedQuery(name = "EmpSkills.findBySkillStatus", query = "SELECT e FROM EmpSkills e WHERE e.skillStatus = :skillStatus")
    , @NamedQuery(name = "EmpSkills.findBySkillUpdatedAt", query = "SELECT e FROM EmpSkills e WHERE e.skillUpdatedAt = :skillUpdatedAt")
    , @NamedQuery(name = "EmpSkills.findBySkillUserId", query = "SELECT e FROM EmpSkills e WHERE e.skillUserId = :skillUserId")
    , @NamedQuery(name = "EmpSkills.findBySkillUserName", query = "SELECT e FROM EmpSkills e WHERE e.skillUserName = :skillUserName")
    , @NamedQuery(name = "EmpSkills.findBySkillYearOfPassing", query = "SELECT e FROM EmpSkills e WHERE e.skillYearOfPassing = :skillYearOfPassing")
    , @NamedQuery(name = "EmpSkills.findBySkillJobappId", query = "SELECT e FROM EmpSkills e WHERE e.skillJobappId = :skillJobappId")})
public class EmpSkills implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "skill_id")
    private Integer skillId;
    @Column(name = "skill_college_uni_institute_name")
    private String skillCollegeUniInstituteName;
    @CreationTimestamp
    @Column(name = "skill_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date skillCreatedAt;
    @Column(name = "skill_division_percentage_of_marks")
    private String skillDivisionPercentageOfMarks;
    @Column(name = "skill_emp_id")
    private Integer skillEmpId;
    @Column(name = "skill_examination")
    private String skillExamination;
    @Column(name = "skill_qualification")
    private String skillQualification;
    @Column(name = "skill_status")
    private Short skillStatus;
    @UpdateTimestamp
    @Column(name = "skill_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date skillUpdatedAt;
    @Column(name = "skill_user_id")
    private Integer skillUserId;
    @Column(name = "skill_user_name")
    private String skillUserName;
    @Column(name = "skill_year_of_passing")
    private String skillYearOfPassing;
    @Column(name = "skill_jobapp_id")
    private Integer skillJobappId;

    public EmpSkills() {
    }

    public EmpSkills(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getSkillCollegeUniInstituteName() {
        return skillCollegeUniInstituteName;
    }

    public void setSkillCollegeUniInstituteName(String skillCollegeUniInstituteName) {
        this.skillCollegeUniInstituteName = skillCollegeUniInstituteName;
    }

    public Date getSkillCreatedAt() {
        return skillCreatedAt;
    }

    public void setSkillCreatedAt(Date skillCreatedAt) {
        this.skillCreatedAt = skillCreatedAt;
    }

    public String getSkillDivisionPercentageOfMarks() {
        return skillDivisionPercentageOfMarks;
    }

    public void setSkillDivisionPercentageOfMarks(String skillDivisionPercentageOfMarks) {
        this.skillDivisionPercentageOfMarks = skillDivisionPercentageOfMarks;
    }

    public Integer getSkillEmpId() {
        return skillEmpId;
    }

    public void setSkillEmpId(Integer skillEmpId) {
        this.skillEmpId = skillEmpId;
    }

    public String getSkillExamination() {
        return skillExamination;
    }

    public void setSkillExamination(String skillExamination) {
        this.skillExamination = skillExamination;
    }

    public String getSkillQualification() {
        return skillQualification;
    }

    public void setSkillQualification(String skillQualification) {
        this.skillQualification = skillQualification;
    }

    public Short getSkillStatus() {
        return skillStatus;
    }

    public void setSkillStatus(Short skillStatus) {
        this.skillStatus = skillStatus;
    }

    public Date getSkillUpdatedAt() {
        return skillUpdatedAt;
    }

    public void setSkillUpdatedAt(Date skillUpdatedAt) {
        this.skillUpdatedAt = skillUpdatedAt;
    }

    public Integer getSkillUserId() {
        return skillUserId;
    }

    public void setSkillUserId(Integer skillUserId) {
        this.skillUserId = skillUserId;
    }

    public String getSkillUserName() {
        return skillUserName;
    }

    public void setSkillUserName(String skillUserName) {
        this.skillUserName = skillUserName;
    }

    public String getSkillYearOfPassing() {
        return skillYearOfPassing;
    }

    public void setSkillYearOfPassing(String skillYearOfPassing) {
        this.skillYearOfPassing = skillYearOfPassing;
    }

    public Integer getSkillJobappId() {
        return skillJobappId;
    }

    public void setSkillJobappId(Integer skillJobappId) {
        this.skillJobappId = skillJobappId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skillId != null ? skillId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpSkills)) {
            return false;
        }
        EmpSkills other = (EmpSkills) object;
        if ((this.skillId == null && other.skillId != null) || (this.skillId != null && !this.skillId.equals(other.skillId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.EmpSkills[ skillId=" + skillId + " ]";
    }
    
}
