///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.tcInterviewform.TCInterviewForm.model;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.xml.bind.annotation.XmlRootElement;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
///**
// *
// * @author teamd
// */
//@Entity
//@Table(name = "client_agreement")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "ClientAgreement.findAll", query = "SELECT c FROM ClientAgreement c")
//    , @NamedQuery(name = "ClientAgreement.findById", query = "SELECT c FROM ClientAgreement c WHERE c.id = :id")
//    , @NamedQuery(name = "ClientAgreement.findByCreatedAt", query = "SELECT c FROM ClientAgreement c WHERE c.createdAt = :createdAt")
//    , @NamedQuery(name = "ClientAgreement.findByUpdatedAt", query = "SELECT c FROM ClientAgreement c WHERE c.updatedAt = :updatedAt")
//    , @NamedQuery(name = "ClientAgreement.findByClientName", query = "SELECT c FROM ClientAgreement c WHERE c.clientName = :clientName")
//    , @NamedQuery(name = "ClientAgreement.findByContactPersonName", query = "SELECT c FROM ClientAgreement c WHERE c.contactPersonName = :contactPersonName")
//    , @NamedQuery(name = "ClientAgreement.findByContactPersonContact", query = "SELECT c FROM ClientAgreement c WHERE c.contactPersonContact = :contactPersonContact")
//    , @NamedQuery(name = "ClientAgreement.findByClientEmailId", query = "SELECT c FROM ClientAgreement c WHERE c.clientEmailId = :clientEmailId")
//    , @NamedQuery(name = "ClientAgreement.findByAgreementStartDate", query = "SELECT c FROM ClientAgreement c WHERE c.agreementStartDate = :agreementStartDate")
//    , @NamedQuery(name = "ClientAgreement.findByAgreementYear", query = "SELECT c FROM ClientAgreement c WHERE c.agreementYear = :agreementYear")
//    , @NamedQuery(name = "ClientAgreement.findByAgreementCopyId", query = "SELECT c FROM ClientAgreement c WHERE c.agreementCopyId = :agreementCopyId")
//    , @NamedQuery(name = "ClientAgreement.findByAgreementExpiryDate", query = "SELECT c FROM ClientAgreement c WHERE c.agreementExpiryDate = :agreementExpiryDate")
//    , @NamedQuery(name = "ClientAgreement.findByAgreementRenewalDate", query = "SELECT c FROM ClientAgreement c WHERE c.agreementRenewalDate = :agreementRenewalDate")
//    , @NamedQuery(name = "ClientAgreement.findByTcContactPersonName", query = "SELECT c FROM ClientAgreement c WHERE c.tcContactPersonName = :tcContactPersonName")
//    , @NamedQuery(name = "ClientAgreement.findByTcContactPersonNumber", query = "SELECT c FROM ClientAgreement c WHERE c.tcContactPersonNumber = :tcContactPersonNumber")
//    , @NamedQuery(name = "ClientAgreement.findByTcContactPersonEmail", query = "SELECT c FROM ClientAgreement c WHERE c.tcContactPersonEmail = :tcContactPersonEmail")
//    , @NamedQuery(name = "ClientAgreement.findByLastModifiedBy", query = "SELECT c FROM ClientAgreement c WHERE c.lastModifiedBy = :lastModifiedBy")
//    , @NamedQuery(name = "ClientAgreement.findByStatus", query = "SELECT c FROM ClientAgreement c WHERE c.status = :status")})
//public class ClientAgreement implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Integer id;
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreationTimestamp
//    private Date createdAt;
//    @Column(name = "updated_at")
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//    @Column(name = "client_name")
//    private String clientName;
//    @Column(name = "contact_person_name")
//    private String contactPersonName;
//    @Column(name = "contact_person_contact")
//    private String contactPersonContact;
//    @Column(name = "client_email_id")
//    private String clientEmailId;
//    @Column(name = "agreement_start_date")
//    private String agreementStartDate;
//    @Column(name = "agreement_year")
//    private String agreementYear;
//    @Column(name = "agreement_copy_id")
//    private Integer agreementCopyId;
//    @Column(name = "agreement_expiry_date")
//    private String agreementExpiryDate;
//    @Column(name = "agreement_renewal_date")
//    private String agreementRenewalDate;
//    @Column(name = "tc_contact_person_name")
//    private String tcContactPersonName;
//    @Column(name = "tc_contact_person_number")
//    private String tcContactPersonNumber;
//    @Column(name = "tc_contact_person_email")
//    private String tcContactPersonEmail;
//    @Column(name = "last_modified_by")
//    private String lastModifiedBy;
//    @Column(name = "status")
//    private String status;
//
//    public ClientAgreement() {
//    }
//
//    public ClientAgreement(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
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
//    public String getClientName() {
//        return clientName;
//    }
//
//    public void setClientName(String clientName) {
//        this.clientName = clientName;
//    }
//
//    public String getContactPersonName() {
//        return contactPersonName;
//    }
//
//    public void setContactPersonName(String contactPersonName) {
//        this.contactPersonName = contactPersonName;
//    }
//
//    public String getContactPersonContact() {
//        return contactPersonContact;
//    }
//
//    public void setContactPersonContact(String contactPersonContact) {
//        this.contactPersonContact = contactPersonContact;
//    }
//
//    public String getClientEmailId() {
//        return clientEmailId;
//    }
//
//    public void setClientEmailId(String clientEmailId) {
//        this.clientEmailId = clientEmailId;
//    }
//
//    public String getAgreementStartDate() {
//        return agreementStartDate;
//    }
//
//    public void setAgreementStartDate(String agreementStartDate) {
//        this.agreementStartDate = agreementStartDate;
//    }
//
//    public String getAgreementYear() {
//        return agreementYear;
//    }
//
//    public void setAgreementYear(String agreementYear) {
//        this.agreementYear = agreementYear;
//    }
//
//    public Integer getAgreementCopyId() {
//        return agreementCopyId;
//    }
//
//    public void setAgreementCopyId(Integer agreementCopyId) {
//        this.agreementCopyId = agreementCopyId;
//    }
//
//    public String getAgreementExpiryDate() {
//        return agreementExpiryDate;
//    }
//
//    public void setAgreementExpiryDate(String agreementExpiryDate) {
//        this.agreementExpiryDate = agreementExpiryDate;
//    }
//
//    public String getAgreementRenewalDate() {
//        return agreementRenewalDate;
//    }
//
//    public void setAgreementRenewalDate(String agreementRenewalDate) {
//        this.agreementRenewalDate = agreementRenewalDate;
//    }
//
//    public String getTcContactPersonName() {
//        return tcContactPersonName;
//    }
//
//    public void setTcContactPersonName(String tcContactPersonName) {
//        this.tcContactPersonName = tcContactPersonName;
//    }
//
//    public String getTcContactPersonNumber() {
//        return tcContactPersonNumber;
//    }
//
//    public void setTcContactPersonNumber(String tcContactPersonNumber) {
//        this.tcContactPersonNumber = tcContactPersonNumber;
//    }
//
//    public String getTcContactPersonEmail() {
//        return tcContactPersonEmail;
//    }
//
//    public void setTcContactPersonEmail(String tcContactPersonEmail) {
//        this.tcContactPersonEmail = tcContactPersonEmail;
//    }
//
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
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
//        if (!(object instanceof ClientAgreement)) {
//            return false;
//        }
//        ClientAgreement other = (ClientAgreement) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.tcInterviewform.TCInterviewForm.model.ClientAgreement[ id=" + id + " ]";
//    }
//    
//}
