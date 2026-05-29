/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.model.LetterIssue;

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

/**
 *
 * @author teamd
 */
@Entity
@Table(name = "issueletter_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IssueletterMaster.findAll", query = "SELECT i FROM IssueletterMaster i")
    , @NamedQuery(name = "IssueletterMaster.findById", query = "SELECT i FROM IssueletterMaster i WHERE i.id = :id")
    , @NamedQuery(name = "IssueletterMaster.findByCreatedAt", query = "SELECT i FROM IssueletterMaster i WHERE i.createdAt = :createdAt")
    , @NamedQuery(name = "IssueletterMaster.findByDocumentName", query = "SELECT i FROM IssueletterMaster i WHERE i.documentName = :documentName")
    , @NamedQuery(name = "IssueletterMaster.findByDocumentId", query = "SELECT i FROM IssueletterMaster i WHERE i.documentId = :documentId")
    , @NamedQuery(name = "IssueletterMaster.findByForm", query = "SELECT i FROM IssueletterMaster i WHERE i.form = :form")
    , @NamedQuery(name = "IssueletterMaster.findByFormId", query = "SELECT i FROM IssueletterMaster i WHERE i.formId = :formId")
    , @NamedQuery(name = "IssueletterMaster.findByIssueBy", query = "SELECT i FROM IssueletterMaster i WHERE i.issueBy = :issueBy")})
public class IssueletterMaster implements Serializable {

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
    @Column(name = "document_name")
    private String documentName;
    @Column(name = "document_id")
    private String documentId;
    @Column(name = "form")
    private String form;
    @Column(name = "form_id")
    private Integer formId;
    @Column(name = "issue_By")
    private String issueBy;

    public IssueletterMaster() {
    }

    public IssueletterMaster(Integer id) {
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

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Integer getFormId() {
        return formId;
    }

    public void setFormId(Integer formId) {
        this.formId = formId;
    }

    public String getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(String issueBy) {
        this.issueBy = issueBy;
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
        if (!(object instanceof IssueletterMaster)) {
            return false;
        }
        IssueletterMaster other = (IssueletterMaster) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.LetterIssue.IssueletterMaster[ id=" + id + " ]";
    }
    
}
