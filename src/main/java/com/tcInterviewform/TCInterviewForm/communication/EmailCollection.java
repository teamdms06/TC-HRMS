/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.communication;

import java.util.Date;

/**
 *
 * @author SIF
 */
public class EmailCollection {

    private Date created_date;
    private String TO;
    private String CC;
    private String SUBJECT;
    private String applicantName;
    private String aPhoneNo;
    private String aEmailId;
    private String gender;
    private String refferBy;
    private String addr;
    private String city;
    private String state;
    private String expFresher;
    private String applyFor;

    public EmailCollection(Date created_date, String TO, String CC, String SUBJECT, String applicantName, String aPhoneNo, String aEmailId, String gender, String refferBy, String addr, String city, String state, String expFresher, String applyFor) {
        this.created_date = created_date;
        this.TO = TO;
        this.CC = CC;
        this.SUBJECT = SUBJECT;
        this.applicantName = applicantName;
        this.aPhoneNo = aPhoneNo;
        this.aEmailId = aEmailId;
        this.gender = gender;
        this.refferBy = refferBy;
        this.addr = addr;
        this.city = city;
        this.state = state;
        this.expFresher = expFresher;
        this.applyFor = applyFor;
    }

    

    public EmailCollection() {
    }
    
    

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getTO() {
        return TO;
    }

    public void setTO(String TO) {
        this.TO = TO;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getaPhoneNo() {
        return aPhoneNo;
    }

    public void setaPhoneNo(String aPhoneNo) {
        this.aPhoneNo = aPhoneNo;
    }

    public String getaEmailId() {
        return aEmailId;
    }

    public void setaEmailId(String aEmailId) {
        this.aEmailId = aEmailId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRefferBy() {
        return refferBy;
    }

    public void setRefferBy(String refferBy) {
        this.refferBy = refferBy;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExpFresher() {
        return expFresher;
    }

    public void setExpFresher(String expFresher) {
        this.expFresher = expFresher;
    }

    public String getApplyFor() {
        return applyFor;
    }

    public void setApplyFor(String applyFor) {
        this.applyFor = applyFor;
    }
    

}
