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
 * @author SIF
 */
@Entity
@Table(name = "resumes_master")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResumesMaster.findAll", query = "SELECT r FROM ResumesMaster r")
    , @NamedQuery(name = "ResumesMaster.findById", query = "SELECT r FROM ResumesMaster r WHERE r.id = :id")
    , @NamedQuery(name = "ResumesMaster.findByFileName", query = "SELECT r FROM ResumesMaster r WHERE r.fileName = :fileName")})
public class ResumesMaster implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "cv_url")
    private String cvUrl;
//    @Column(name = "file_type")
//    private String fileType;
//    @Lob
//    @Column(name = "data")
//    private byte[] data;
    @CreationTimestamp
    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    public ResumesMaster() {
    }

//    public ResumesMaster(String fileName, byte[] data) {
//        this.fileName = fileName;
//        this.data = data;
//    }

    public ResumesMaster(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

//    public byte[] getData() {
//        return data;
//    }
//
//    public void setData(byte[] data) {
//        this.data = data;
//    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCvUrl() {
        return cvUrl;
    }

//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    @Override
    public String toString() {
        return "ResumesMaster{" + "id=" + id + ", fileName=" + fileName + ", cvUrl=" + cvUrl + ", uploadDate=" + uploadDate + '}';
    }

   


  

  

}
