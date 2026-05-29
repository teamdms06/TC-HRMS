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

/**
 *
 * @author SIF
 */
@Entity
@Table(name = "state")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s")
    , @NamedQuery(name = "State.findById", query = "SELECT s FROM State s WHERE s.id = :id")
    , @NamedQuery(name = "State.findByCreatedAt", query = "SELECT s FROM State s WHERE s.createdAt = :createdAt")
    , @NamedQuery(name = "State.findByName", query = "SELECT s FROM State s WHERE s.name = :name")
    , @NamedQuery(name = "State.findByDescription", query = "SELECT s FROM State s WHERE s.description = :description")
    , @NamedQuery(name = "State.findByCountryId", query = "SELECT s FROM State s WHERE s.countryId = :countryId")
    , @NamedQuery(name = "State.findByUpdatedAt", query = "SELECT s FROM State s WHERE s.updatedAt = :updatedAt")
    , @NamedQuery(name = "State.findByEnabled", query = "SELECT s FROM State s WHERE s.enabled = :enabled")
    , @NamedQuery(name = "State.findByZone", query = "SELECT s FROM State s WHERE s.zone = :zone")})
public class State implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Created_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;
    @Column(name = "Country_Id")
    private Integer countryId;
    @Column(name = "Updated_At")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "Enabled")
    private Boolean enabled;
    @Column(name = "Zone")
    private String zone;

    public State() {
    }

    public State(Integer id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
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
        if (!(object instanceof State)) {
            return false;
        }
        State other = (State) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tcInterviewform.TCInterviewForm.model.State[ id=" + id + " ]";
    }
    
}
