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

/**
 *
 * @author SIF
 */
@Entity
@Table(name = "otp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Otp.findAll", query = "SELECT o FROM Otp o")
    , @NamedQuery(name = "Otp.findByOtpId", query = "SELECT o FROM Otp o WHERE o.otpId = :otpId")
    , @NamedQuery(name = "Otp.findByOtpCreatedAt", query = "SELECT o FROM Otp o WHERE o.otpCreatedAt = :otpCreatedAt")
    , @NamedQuery(name = "Otp.findByOtpPhone", query = "SELECT o FROM Otp o WHERE o.otpPhone = :otpPhone")
    , @NamedQuery(name = "Otp.findByOtpStatus", query = "SELECT o FROM Otp o WHERE o.otpStatus = :otpStatus")
    , @NamedQuery(name = "Otp.findByOtpText", query = "SELECT o FROM Otp o WHERE o.otpText = :otpText")
    , @NamedQuery(name = "Otp.findByOtpUpdatedAt", query = "SELECT o FROM Otp o WHERE o.otpUpdatedAt = :otpUpdatedAt")
    , @NamedQuery(name = "Otp.findByOtpValue", query = "SELECT o FROM Otp o WHERE o.otpValue = :otpValue")})
public class Otp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "otp_id")
    private Integer otpId;
    @Column(name = "otp_created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpCreatedAt;
    @Column(name = "otp_phone")
    private String otpPhone;
    @Column(name = "otp_status")
    private Short otpStatus;
    @Column(name = "otp_text")
    private String otpText;
    @Column(name = "otp_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpUpdatedAt;
    @Column(name = "otp_value")
    private String otpValue;

    public Otp() {
    }

    public Otp(Integer otpId) {
        this.otpId = otpId;
    }

    public Integer getOtpId() {
        return otpId;
    }

    public void setOtpId(Integer otpId) {
        this.otpId = otpId;
    }

    public Date getOtpCreatedAt() {
        return otpCreatedAt;
    }

    public void setOtpCreatedAt(Date otpCreatedAt) {
        this.otpCreatedAt = otpCreatedAt;
    }

    public String getOtpPhone() {
        return otpPhone;
    }

    public void setOtpPhone(String otpPhone) {
        this.otpPhone = otpPhone;
    }

    public Short getOtpStatus() {
        return otpStatus;
    }

    public void setOtpStatus(Short otpStatus) {
        this.otpStatus = otpStatus;
    }

    public String getOtpText() {
        return otpText;
    }

    public void setOtpText(String otpText) {
        this.otpText = otpText;
    }

    public Date getOtpUpdatedAt() {
        return otpUpdatedAt;
    }

    public void setOtpUpdatedAt(Date otpUpdatedAt) {
        this.otpUpdatedAt = otpUpdatedAt;
    }

    public String getOtpValue() {
        return otpValue;
    }

    public void setOtpValue(String otpValue) {
        this.otpValue = otpValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (otpId != null ? otpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otp)) {
            return false;
        }
        Otp other = (Otp) object;
        if ((this.otpId == null && other.otpId != null) || (this.otpId != null && !this.otpId.equals(other.otpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.Otp[ otpId=" + otpId + " ]";
    }
    
}
