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
//import javax.persistence.Lob;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.xml.bind.annotation.XmlRootElement;
//import org.hibernate.annotations.CreationTimestamp;
//
///**
// *
// * @author teamd
// */
//@Entity
//@Table(name = "document_master")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "DocumentMaster.findAll", query = "SELECT d FROM DocumentMaster d")
//    , @NamedQuery(name = "DocumentMaster.findById", query = "SELECT d FROM DocumentMaster d WHERE d.id = :id")
//    , @NamedQuery(name = "DocumentMaster.findByUploadDate", query = "SELECT d FROM DocumentMaster d WHERE d.uploadDate = :uploadDate")
//    , @NamedQuery(name = "DocumentMaster.findByFileName", query = "SELECT d FROM DocumentMaster d WHERE d.fileName = :fileName")})
//public class DocumentMaster implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Integer id;
//    @Column(name = "upload_date")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date uploadDate;
//    @Column(name = "file_name")
//    private String fileName;
//    @Lob
//    @Column(name = "client_agreement")
//    private byte[] clientAgreement;
//
//    public DocumentMaster() {
//    }
//
//    public DocumentMaster(String fileName, byte[] clientAgreement) {
//        this.fileName = fileName;
//        this.clientAgreement = clientAgreement;
//    }
//
//    
//    public DocumentMaster(Integer id) {
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
//    public Date getUploadDate() {
//        return uploadDate;
//    }
//
//    public void setUploadDate(Date uploadDate) {
//        this.uploadDate = uploadDate;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public byte[] getClientAgreement() {
//        return clientAgreement;
//    }
//
//    public void setClientAgreement(byte[] clientAgreement) {
//        this.clientAgreement = clientAgreement;
//    }
//
//  
//
//    @Override
//    public String toString() {
//        return "com.tcInterviewform.TCInterviewForm.model.DocumentMaster[ id=" + id + " ]";
//    }
//    
//}
