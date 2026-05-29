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
 * @author teamd
 */
@Entity
@Table(name = "meta_adds_lead")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetaAddsLead.findAll", query = "SELECT m FROM MetaAddsLead m")
    , @NamedQuery(name = "MetaAddsLead.findById", query = "SELECT m FROM MetaAddsLead m WHERE m.id = :id")
    , @NamedQuery(name = "MetaAddsLead.findByCreatedAt", query = "SELECT m FROM MetaAddsLead m WHERE m.createdAt = :createdAt")
    , @NamedQuery(name = "MetaAddsLead.findByUpdatedAt", query = "SELECT m FROM MetaAddsLead m WHERE m.updatedAt = :updatedAt")
    , @NamedQuery(name = "MetaAddsLead.findByEmail", query = "SELECT m FROM MetaAddsLead m WHERE m.email = :email")
    , @NamedQuery(name = "MetaAddsLead.findByName", query = "SELECT m FROM MetaAddsLead m WHERE m.name = :name")
    , @NamedQuery(name = "MetaAddsLead.findByPhoneNumber", query = "SELECT m FROM MetaAddsLead m WHERE m.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "MetaAddsLead.findByLocation", query = "SELECT m FROM MetaAddsLead m WHERE m.location = :location")
    , @NamedQuery(name = "MetaAddsLead.findByLanguage", query = "SELECT m FROM MetaAddsLead m WHERE m.language = :language")
    , @NamedQuery(name = "MetaAddsLead.findByExperience", query = "SELECT m FROM MetaAddsLead m WHERE m.experience = :experience")
    , @NamedQuery(name = "MetaAddsLead.findBySalaryExpectations", query = "SELECT m FROM MetaAddsLead m WHERE m.salaryExpectations = :salaryExpectations")
    , @NamedQuery(name = "MetaAddsLead.findByStatus", query = "SELECT m FROM MetaAddsLead m WHERE m.status = :status")
    , @NamedQuery(name = "MetaAddsLead.findByAdsetName", query = "SELECT m FROM MetaAddsLead m WHERE m.adsetName = :adsetName")
    , @NamedQuery(name = "MetaAddsLead.findByCampaignName", query = "SELECT m FROM MetaAddsLead m WHERE m.campaignName = :campaignName")
    , @NamedQuery(name = "MetaAddsLead.findByFormName", query = "SELECT m FROM MetaAddsLead m WHERE m.formName = :formName")})
public class MetaAddsLead implements Serializable {

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
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "location")
    private String location;
    @Column(name = "language")
    private String language;
    @Column(name = "experience")
    private String experience;
    @Lob
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "salary_expectations")
    private String salaryExpectations;
    @Column(name = "status")
    private String status;
    @Column(name = "adset_name")
    private String adsetName;
    @Column(name = "campaign_name")
    private String campaignName;
    @Column(name = "form_name")
    private String formName;

    public MetaAddsLead() {
    }

    public MetaAddsLead(Integer id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getSalaryExpectations() {
        return salaryExpectations;
    }

    public void setSalaryExpectations(String salaryExpectations) {
        this.salaryExpectations = salaryExpectations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdsetName() {
        return adsetName;
    }

    public void setAdsetName(String adsetName) {
        this.adsetName = adsetName;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
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
        if (!(object instanceof MetaAddsLead)) {
            return false;
        }
        MetaAddsLead other = (MetaAddsLead) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.MetaAddsLead[ id=" + id + " ]";
    }
    
}
