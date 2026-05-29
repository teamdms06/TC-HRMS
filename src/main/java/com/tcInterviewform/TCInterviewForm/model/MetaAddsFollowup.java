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

/**
 *
 * @author teamd
 */
@Entity
@Table(name = "meta_adds_followup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetaAddsFollowup.findAll", query = "SELECT m FROM MetaAddsFollowup m")
    , @NamedQuery(name = "MetaAddsFollowup.findById", query = "SELECT m FROM MetaAddsFollowup m WHERE m.id = :id")
    , @NamedQuery(name = "MetaAddsFollowup.findByCreatedAt", query = "SELECT m FROM MetaAddsFollowup m WHERE m.createdAt = :createdAt")
    , @NamedQuery(name = "MetaAddsFollowup.findByLeadId", query = "SELECT m FROM MetaAddsFollowup m WHERE m.leadId = :leadId")
    , @NamedQuery(name = "MetaAddsFollowup.findByCallStatus", query = "SELECT m FROM MetaAddsFollowup m WHERE m.callStatus = :callStatus")
    , @NamedQuery(name = "MetaAddsFollowup.findByStatus", query = "SELECT m FROM MetaAddsFollowup m WHERE m.status = :status")
    , @NamedQuery(name = "MetaAddsFollowup.findByActionBy", query = "SELECT m FROM MetaAddsFollowup m WHERE m.actionBy = :actionBy")
    , @NamedQuery(name = "MetaAddsFollowup.findByAttempts", query = "SELECT m FROM MetaAddsFollowup m WHERE m.attempts = :attempts")
    , @NamedQuery(name = "MetaAddsFollowup.findByLanguage", query = "SELECT m FROM MetaAddsFollowup m WHERE m.language = :language")
    , @NamedQuery(name = "MetaAddsFollowup.findBySalaryExpectation", query = "SELECT m FROM MetaAddsFollowup m WHERE m.salaryExpectation = :salaryExpectation")})
public class MetaAddsFollowup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @CreationTimestamp
    @Column(name = "created_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "lead_id")
    private Integer leadId;
    @Column(name = "call_status")
    private String callStatus;
    @Column(name = "status")
    private String status;
    @Column(name = "action_by")
    private String actionBy;
    @Column(name = "attempts")
    private Integer attempts;
    @Lob
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "language")
    private String language;
    @Column(name = "salary_expectation")
    private String salaryExpectation;

    public MetaAddsFollowup() {
    }

    public MetaAddsFollowup(Integer id) {
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

    public Integer getLeadId() {
        return leadId;
    }

    public void setLeadId(Integer leadId) {
        this.leadId = leadId;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSalaryExpectation() {
        return salaryExpectation;
    }

    public void setSalaryExpectation(String salaryExpectation) {
        this.salaryExpectation = salaryExpectation;
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
        if (!(object instanceof MetaAddsFollowup)) {
            return false;
        }
        MetaAddsFollowup other = (MetaAddsFollowup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.MetaAddsFollowup[ id=" + id + " ]";
    }
    
}
